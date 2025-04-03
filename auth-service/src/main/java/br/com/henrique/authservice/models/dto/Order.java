package br.com.henrique.authservice.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private String status;
    private List<ItemOrder> items;
    private Address address;

    public Order() {
    }


    public Order(Long id, Long userId, LocalDateTime orderDate, String status, List<ItemOrder> items, Address address) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<ItemOrder> getItems() {
        return items;
    }

    public void setItems(List<ItemOrder> items) {
        this.items = items;
    }

    public Address getAddress() {return address;}

    public void setAddress(Address address) {this.address = address;}

}
