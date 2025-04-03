package br.com.henrique.authservice.models.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Book implements Serializable {

    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private BigDecimal price;
    private Integer stockQuantity;
    private Author author;
    private Publisher publisher;
    private Category category;

    public Book() {
    }

    public Book(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.category = book.getCategory();
    }

    public Book(Long id, String title, String isbn, LocalDate publicationDate, BigDecimal price, Integer stockQuantity, Author author, Publisher publisher, Category category) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
