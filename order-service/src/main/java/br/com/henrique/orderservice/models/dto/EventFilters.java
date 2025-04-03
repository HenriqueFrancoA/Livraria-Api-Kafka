package br.com.henrique.orderservice.models.dto;

public class EventFilters {

    private Long orderId;
    private String transactionId;

    public EventFilters() {
    }

    public EventFilters(Long orderId, String transactionId) {
        this.orderId = orderId;
        this.transactionId = transactionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
