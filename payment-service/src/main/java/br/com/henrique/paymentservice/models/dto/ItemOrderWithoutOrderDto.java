package br.com.henrique.paymentservice.models.dto;

import java.math.BigDecimal;

public class ItemOrderWithoutOrderDto {

    private BookDto book;
    private Integer quantity;
    private BigDecimal unitPrice;

    public ItemOrderWithoutOrderDto() {
    }

    public ItemOrderWithoutOrderDto(Integer quantity, BigDecimal unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
