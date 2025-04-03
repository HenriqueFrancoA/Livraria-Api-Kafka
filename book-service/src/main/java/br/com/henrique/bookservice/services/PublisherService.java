package br.com.henrique.bookservice.services;

import br.com.henrique.bookservice.models.Publisher;
import br.com.henrique.bookservice.repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PublisherService extends GenericService<Publisher, Long> {

    public PublisherService(PublisherRepository publisherRepository) {
        super(publisherRepository);
    }

    private final Logger logger = Logger.getLogger(PublisherService.class.getName());

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Publisher update(Long id, Publisher publisher, String simpleName) {
        logger.info("Atualizando Editora, ID: " + id);
        Publisher pub = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado."));
        pub.setCountry(publisher.getCountry());
        pub.setName(publisher.getName());

        return publisherRepository.save(pub);
    }
}
