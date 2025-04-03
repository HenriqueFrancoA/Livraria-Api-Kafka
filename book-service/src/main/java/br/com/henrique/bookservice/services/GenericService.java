package br.com.henrique.bookservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class GenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    private final Logger logger = Logger.getLogger(GenericService.class.getName());

    public GenericService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Optional<T> findById(ID id, String simpleName) {
        logger.info("Procurando pelo ID: " + id + ".  Chamada por: " + simpleName);
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Entidade não encontrada.")));
    }

    public List<T> findAll(String simpleName) {
        logger.info("Retornando todas entidades..."  + ".  Chamada por: " + simpleName);
        return repository.findAll();
    }

    public T save(T entity, String simpleName) {
        logger.info("Criando entidade " + entity.getClass().getSimpleName() + ".  Chamada por: " + simpleName);
        return repository.save(entity);
    }

    public T update(ID id, T entity, String simpleName) {
        logger.info("Atualizando entidade " + entity.getClass().getSimpleName() + " ID: " + id + ".  Chamada por: " + simpleName);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entidade não encontrada.");
        }
        return repository.save(entity);
    }

    public void delete(ID id, String simpleName) {
        logger.info("Deletando entidade ID: " + id + ".  Chamada por: " + simpleName);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entidade não encontrada.");
        }
        repository.deleteById(id);
    }
}