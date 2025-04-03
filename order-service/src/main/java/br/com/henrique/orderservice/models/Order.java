package br.com.henrique.orderservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private String transactionId;

    private String status;

    @Transient
    private List<ItemOrder> items;

    @Column(name = "address_id")
    private Long addressId;

    public Order() {
    }

    public Order(String transactionId, Long userId, Long addressId, LocalDateTime localDateTime, String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderDate = localDateTime;
        this.status = status;
        this.addressId = addressId;
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

}
