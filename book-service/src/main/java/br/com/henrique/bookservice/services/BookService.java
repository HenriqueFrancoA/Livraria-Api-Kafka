package br.com.henrique.bookservice.services;

import br.com.henrique.bookservice.enums.ESagaStatus;
import br.com.henrique.bookservice.exceptions.ValidationException;
import br.com.henrique.bookservice.mapper.DozerMapper;
import br.com.henrique.bookservice.models.Author;
import br.com.henrique.bookservice.models.Book;
import br.com.henrique.bookservice.models.Category;
import br.com.henrique.bookservice.models.Publisher;
import br.com.henrique.bookservice.models.dto.BookPriceQuantityDto;
import br.com.henrique.bookservice.models.dto.BookWithAuthorPublisherCategoryDto;
import br.com.henrique.bookservice.models.dto.Event;
import br.com.henrique.bookservice.models.dto.History;
import br.com.henrique.bookservice.producer.KafkaProducer;
import br.com.henrique.bookservice.repositories.AuthorRepository;
import br.com.henrique.bookservice.repositories.BookRepository;
import br.com.henrique.bookservice.repositories.CategoryRepository;
import br.com.henrique.bookservice.repositories.PublisherRepository;
import br.com.henrique.bookservice.utils.JsonUtil;
import br.com.henrique.bookservice.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import static br.com.henrique.bookservice.enums.ESagaStatus.SUCCESS;

@Service
public class BookService  {

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    private static final String CURRENT_SOURCE = "BOOK_SERVICE";

    private final JsonUtil jsonUtil;

    private final KafkaProducer kafkaProducer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public BookService(JsonUtil jsonUtil, KafkaProducer kafkaProducer) {
        this.jsonUtil = jsonUtil;
        this.kafkaProducer = kafkaProducer;
    }

    public Book update(Long id, BookPriceQuantityDto bookPriceQuantity) {
        logger.info("Atualizando Livro, ID: " + id);
        Book bo = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado."));

        ValidationUtils.verifyQuantity(bookPriceQuantity.getStockQuantity());
        ValidationUtils.verifyValue(bookPriceQuantity.getPrice());

        bo.setPrice(bookPriceQuantity.getPrice());
        bo.setStockQuantity(bookPriceQuantity.getStockQuantity());

        return bookRepository.save(bo);
    }

    public Book save(BookWithAuthorPublisherCategoryDto bookWithAuthorPublisherCategory) {
        logger.info("Criando Livro.");

        Author author = authorRepository.findById(bookWithAuthorPublisherCategory.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("ID do Autor não encontrado."));

        Publisher publisher = publisherRepository.findById(bookWithAuthorPublisherCategory.getPublisherId())
                .orElseThrow(() -> new EntityNotFoundException("ID da Editora não encontrada."));
        
        Category category = categoryRepository.findById(bookWithAuthorPublisherCategory.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("ID da Categoria não encontrada."));
        
        Book book = new Book(
                bookWithAuthorPublisherCategory,
                author,
                publisher,
                category
        );

        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        logger.info("Retornando todos livros...");
        return DozerMapper.parseListObjects(bookRepository.findAll(), Book.class);
    }

    public void delete(Long id) {
        logger.info("Deletando livro do ID: " + id);
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado.");
        }
        bookRepository.deleteById(id);
    }


    public Book findById(Long id) {
        logger.info("Procurando Endereço do ID: " + id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do livro não encontrado."));

        return DozerMapper.parseObject(book, Book.class);
    }

    public void checkInventoryAvailable(Event event) {
        try{
            checkBookValidation(event);
            stockQuantityAvailable(event);

            event.setStockModified(true);
            handleSuccess(event);
        } catch (Exception ex) {
            logger.info("Erro ao validar items do pedido: " + ex.getMessage());
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }
        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkBookValidation(Event event) {
        event.getPayload()
                .getItems()
                .forEach(item -> {
                    if (!bookRepository.existsById(item.getBookId())) {
                        throw new EntityNotFoundException("Livro não encontrado, ID: " + item.getBookId());
                    }
                });
    }

    @Transactional
    private void stockQuantityAvailable(Event event) {
        event.getPayload()
                .getItems()
                .forEach(item -> {
                    var book = bookRepository.findById(item.getBookId()).orElseThrow();

                    int bookQuantityUpdated = ValidationUtils.verifyBookQuantity(book.getStockQuantity(), item.getQuantity());
                    book.setStockQuantity(bookQuantityUpdated);

                    bookRepository.save(book);
                });
    }

    public void refundInventoryForOrderCanceled(Event event) {
        try {
            checkBookValidation(event);
            changeStockQuantityToRefund(event);

            event.setStockModified(true);
            handleSuccess(event);
        } catch (Exception ex) {
            logger.info("Erro ao devolver estoque para pedido cancelado: " + ex.getMessage());
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }
        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Estoque atualizado com sucesso!");
    }

    private void addHistory(Event event, String message){
        var history = new History(
                event.getSource(),
                event.getStatus(),
                message,
                LocalDateTime.now()
        );
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(ESagaStatus.ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Falha ao validar items: " + message);
    }

    public void failInventoryAvailable(Event event) {
        event.setStatus(ESagaStatus.FAIL);
        event.setSource(CURRENT_SOURCE);

        try {
            checkBookValidation(event);
            if (event.isStockModified()) {
                changeStockQuantityToRefund(event);
                addHistory(event, "Rollback executado para inventory");
            } else {
                addHistory(event, "Rollback não necessário para inventory: estoque não foi modificado");
            }
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para inventory: " + ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    public void failRefundInventoryForOrderCanceled(Event event) {
        event.setStatus(ESagaStatus.FAIL);
        event.setSource(CURRENT_SOURCE);

        try {
            checkBookValidation(event);
            addHistory(event, "Rollback executado para devolução de estoque: nenhuma alteração necessária");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para devolução de estoque: " + ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event)); // Envia o evento final pro Kafka
    }

    @Transactional
    private void changeStockQuantityToRefund(Event event) {
        event.getPayload()
                .getItems()
                .forEach(item -> {
                    var book = bookRepository.findById(item.getBookId()).orElseThrow();

                    book.setStockQuantity(book.getStockQuantity() + item.getQuantity());
                    bookRepository.save(book);
                });
    }
}
