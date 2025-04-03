package br.com.henrique.orchestratorservice.core.dto;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private String transactionId;
    private String status;
    private List<ItemOrder> items;
    private Long addressId;

    public Order() {
    }

    public Order(Long id, Long userId, LocalDateTime orderDate, String transactionId, String status, List<ItemOrder> items, Long addressId) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.status = status;
        this.items = items;
        this.addressId = addressId;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
