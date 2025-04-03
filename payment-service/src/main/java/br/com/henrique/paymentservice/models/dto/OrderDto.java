package br.com.henrique.paymentservice.models.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private Long id;
    private UserDto user;
    private LocalDateTime orderDate;
    private String status;
    private List<ItemOrderWithoutOrderDto> items;
    private AddressDto address;

    public OrderDto() {
    }

    public OrderDto(Long id, UserDto user, LocalDateTime orderDate, String status, List<ItemOrderWithoutOrderDto> items, AddressDto address) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.address = address;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemOrderWithoutOrderDto> getItems() {
        return items;
    }

    public void setItems(List<ItemOrderWithoutOrderDto> items) {
        this.items = items;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public AddressDto getAddress() {return address;}

    public void setAddress(AddressDto address) {this.address = address;}
}
