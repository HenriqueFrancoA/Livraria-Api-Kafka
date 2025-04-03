package br.com.henrique.paymentservice.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private double totalAmount;
    private int totalItems;

    public Order() {
    }


    public Order(Long id, Long userId, LocalDateTime orderDate, String transactionId, String status, List<ItemOrder> items, Long addressId, double totalAmount, int totalItems) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.status = status;
        this.items = items;
        this.addressId = addressId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("userId")
    public Long getUserId() {
        return userId;
    }

    @JsonProperty("userId")
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

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public Long getAddressId() {return addressId;}

    public void setAddress(Long addressId) {this.addressId = addressId;}

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
