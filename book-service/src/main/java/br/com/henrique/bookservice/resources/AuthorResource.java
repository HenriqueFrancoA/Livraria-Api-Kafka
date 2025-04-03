package br.com.henrique.bookservice.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.henrique.bookservice.models.Author;
import br.com.henrique.bookservice.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autores")
@RestController
@RequestMapping("/api/authors/v1")
public class AuthorResource extends GenericResource<Author, Long> {

    public AuthorResource(AuthorService authorService) {
        super(authorService);
    }

    @Override
    @Operation(summary = "Busca um autor pelo ID e caso exista atualiza o mesmo")
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        return super.update(id, author);
    }

}
