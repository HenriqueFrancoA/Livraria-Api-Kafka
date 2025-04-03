package br.com.henrique.bookservice.models.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class BookWithAuthorPublisherCategoryDto {

    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long authorId;
    private Long publisherId;
    private Long categoryId;

    public BookWithAuthorPublisherCategoryDto() {
    }

    public BookWithAuthorPublisherCategoryDto(String title, String isbn, LocalDate publicationDate, BigDecimal price, Integer stockQuantity, Long authorId, Long publisherId, Long categoryId) {
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.categoryId = categoryId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
