package br.com.henrique.orderservice.models.dto;

public class ItemOrderQuantityDto {

    private Integer quantity;

    public ItemOrderQuantityDto() {
    }

    public ItemOrderQuantityDto(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
