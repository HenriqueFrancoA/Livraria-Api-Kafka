package br.com.henrique.bookservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.henrique.bookservice.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
