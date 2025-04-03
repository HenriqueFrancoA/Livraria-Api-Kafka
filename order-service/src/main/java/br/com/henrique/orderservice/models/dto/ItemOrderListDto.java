package br.com.henrique.orderservice.models.dto;

import java.util.List;

public class ItemOrderListDto {

    private Long orderId;
    private List<ItemOrderWithBookDto> items;


    public ItemOrderListDto() {
    }

    public ItemOrderListDto(Long orderId, List<ItemOrderWithBookDto> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ItemOrderWithBookDto> getItems() {
        return items;
    }

    public void setItems(List<ItemOrderWithBookDto> items) {
        this.items = items;
    }
}
