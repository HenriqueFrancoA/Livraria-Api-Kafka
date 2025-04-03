package br.com.henrique.orderservice.models.dto;

import java.math.BigDecimal;

public class ItemOrderDto{

    private Book book;
    private Integer quantity;
    private BigDecimal unitPrice;

    public ItemOrderDto() {
    }

    public ItemOrderDto(Book book, Integer quantity, BigDecimal unitPrice) {
        this.book = book;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
