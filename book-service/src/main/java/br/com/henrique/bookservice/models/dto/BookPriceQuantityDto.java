package br.com.henrique.bookservice.models.dto;

import java.math.BigDecimal;


public class BookPriceQuantityDto {

    private BigDecimal price;
    private Integer stockQuantity;

    public BookPriceQuantityDto() {
    }

    public BookPriceQuantityDto(BigDecimal price, Integer stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {return stockQuantity;}

    public void setStockQuantity(Integer stockQuantity) {this.stockQuantity = stockQuantity;}
}
