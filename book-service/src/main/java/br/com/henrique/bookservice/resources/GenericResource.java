package br.com.henrique.bookservice.resources;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.henrique.bookservice.services.GenericService;

public abstract class GenericResource<T, ID> {

    protected final GenericService<T, ID> service;

    public GenericResource(GenericService<T, ID> service) {
        this.service = service;
    }

    @Operation(summary = "Busca pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<T>> findById(@PathVariable ID id) {
        return ResponseEntity.ok(service.findById(id, service.getClass().getSimpleName()));
    }

    @Operation(summary = "Busca todas entidades.")
    @GetMapping
    public List<T> findAll() {
        return service.findAll(service.getClass().getSimpleName());
    }

    @Operation(summary = "Cria uma nova entidade.")
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity, service.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @Operation(summary = "Busca a entidade pelo ID e atualiza.")
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        return ResponseEntity.ok(service.update(id, entity, service.getClass().getSimpleName()));
    }

    @Operation(summary = "Deleta a entidade pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id, service.getClass().getSimpleName());
        return ResponseEntity.noContent().build();
    }
}
