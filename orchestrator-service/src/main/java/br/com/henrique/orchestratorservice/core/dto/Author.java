package br.com.henrique.orchestratorservice.core.dto;

public class Author {

    private Long id;
    private String name;
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