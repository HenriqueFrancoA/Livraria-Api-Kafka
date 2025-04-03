package br.com.henrique.orderservice.models.dto;

import java.math.BigDecimal;

public class ItemOrderAddDto {

    private Long bookId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public ItemOrderAddDto() {
    }

    public ItemOrderAddDto(Long bookId, Integer quantity, BigDecimal unitPrice) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
