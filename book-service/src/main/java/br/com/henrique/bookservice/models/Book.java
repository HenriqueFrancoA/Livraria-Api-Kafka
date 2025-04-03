package br.com.henrique.bookservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.henrique.bookservice.models.dto.BookWithAuthorPublisherCategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category")
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

    public Book(BookWithAuthorPublisherCategoryDto bookWithAuthorPublisherCategory, Author author, Publisher publisher, Category category) {
        this.title = bookWithAuthorPublisherCategory.getTitle();
        this.isbn = bookWithAuthorPublisherCategory.getIsbn();
        this.publicationDate = bookWithAuthorPublisherCategory.getPublicationDate();
        this.price = bookWithAuthorPublisherCategory.getPrice();
        this.stockQuantity = bookWithAuthorPublisherCategory.getStockQuantity();
        this.author = author;
        this.publisher = publisher;
        this.category = category;
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
