package br.com.henrique.orderservice.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Itemsorder")
public class ItemOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    public ItemOrder() {
    }

    public ItemOrder(Long orderId, Long bookId, Integer quantity, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ItemOrder(Long id, Long orderId, Long bookId, Integer quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ItemOrder(ItemOrder itemOrder) {
        this.id = itemOrder.getId();
        this.orderId = itemOrder.getOrderId();
        this.bookId = itemOrder.getBookId();
        this.quantity = itemOrder.getQuantity();
        this.unitPrice = itemOrder.getUnitPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
