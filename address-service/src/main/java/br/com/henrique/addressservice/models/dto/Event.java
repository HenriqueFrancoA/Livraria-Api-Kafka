package br.com.henrique.addressservice.models.dto;

import br.com.henrique.addressservice.enums.ESagaStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private String id;
    private String transactionId;
    private Long orderId;
    private Order payload;
    private String source;
    private String sagaType;
    private ESagaStatus status;
    private List<History> eventHistory;
    private LocalDateTime createdAt;

    public Event() {
    }

    public Event(String id, String transactionId, Long orderId, Order payload, String source, String sagaType, ESagaStatus status, List<History> eventHistory, LocalDateTime createdAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.payload = payload;
        this.source = source;
        this.sagaType = sagaType;
        this.status = status;
        this.eventHistory = eventHistory;
        this.createdAt = createdAt;
    }

    public void addToHistory(History history){
        if(eventHistory == null){
            eventHistory = new ArrayList<>();
        }
        eventHistory.add(history);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Order getPayload() {
        return payload;
    }

    public void setPayload(Order payload) {
        this.payload = payload;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSagaType() {
        return sagaType;
    }

    public void setSagaType(String sagaType) {
        this.sagaType = sagaType;
    }

    public ESagaStatus getStatus() {
        return status;
    }

    public void setStatus(ESagaStatus status) {
        this.status = status;
    }

    public List<History> getEventHistory() {
        return eventHistory;
    }

    public void setEventHistory(List<History> eventHistory) {
        this.eventHistory = eventHistory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
