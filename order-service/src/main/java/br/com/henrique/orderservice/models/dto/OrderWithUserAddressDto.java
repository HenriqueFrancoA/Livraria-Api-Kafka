package br.com.henrique.orderservice.models.dto;

import java.util.List;

public class OrderWithUserAddressDto {

    private Long userId;
    private Long addressId;
    private List<ItemOrderWithBookDto> items;

    public OrderWithUserAddressDto() {
    }

    public OrderWithUserAddressDto(Long userId, Long addressId, List<ItemOrderWithBookDto> items, String paymentMethod) {
        this.userId = userId;
        this.addressId = addressId;
        this.items = items;
    }

    public Long getAddressId() {return addressId;}

    public void setAddressId(Long addressId) {this.addressId = addressId;}

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public List<ItemOrderWithBookDto> getItems() {return items;}

    public void setItems(List<ItemOrderWithBookDto> items) {this.items = items;}
}
