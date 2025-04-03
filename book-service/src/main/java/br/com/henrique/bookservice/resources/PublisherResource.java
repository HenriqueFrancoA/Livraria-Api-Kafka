package br.com.henrique.bookservice.resources;

import br.com.henrique.bookservice.models.Publisher;
import br.com.henrique.bookservice.services.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Publicações")
@RestController
@RequestMapping("/api/publishers/v1")
public class PublisherResource extends GenericResource<Publisher, Long>  {

    public PublisherResource(PublisherService publisherService) {
        super(publisherService);
    }

    @Override
    @Operation(summary = "Busca uma editora pelo ID e caso exista atualiza a mesma")
    public ResponseEntity<Publisher> update(@PathVariable Long id, @RequestBody Publisher publisher) {
        return super.update(id, publisher);
    }

}
