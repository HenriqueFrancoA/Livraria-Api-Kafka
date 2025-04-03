package br.com.henrique.bookservice.models;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "Authors")
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 5000)
    private String biography;

    public Author() {
    }

    public Author(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.biography = author.getBiography();
    }

    public Author(Long id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}