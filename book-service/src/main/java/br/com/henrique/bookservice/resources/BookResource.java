package br.com.henrique.bookservice.resources;

import br.com.henrique.bookservice.models.Book;
import br.com.henrique.bookservice.models.dto.BookPriceQuantityDto;
import br.com.henrique.bookservice.models.dto.BookWithAuthorPublisherCategoryDto;
import br.com.henrique.bookservice.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Livros")
@RestController
@RequestMapping("/api/books/v1")
public class BookResource  {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Busca todos livros cadastrados.")
    @GetMapping
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @Operation(summary = "Busca um livro pelo ID.")
    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @Operation(summary = "Busca um livro pelo ID e caso exista atualiza o mesmo")
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookPriceQuantityDto bookPriceQuantity) {
        return ResponseEntity.ok(bookService.update(id, bookPriceQuantity));
    }

    @Operation(summary = "Cria um novo livro")
    @PostMapping()
    public ResponseEntity<Book> create(@RequestBody BookWithAuthorPublisherCategoryDto bookWithAuthorPublisherCategory) {
        Book savedBook = bookService.save(bookWithAuthorPublisherCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(summary = "Deleta o livro pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
