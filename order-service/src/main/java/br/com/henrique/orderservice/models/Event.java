package br.com.henrique.orderservice.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order payload;
    private String source;
    private String status;
    @Column(name = "saga_type") // Novo campo
    private String sagaType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<History> eventHistory;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Event() {
    }

    public Event(String id, String transactionId, Long orderId, Order payload, String source, String status, String sagaType, List<History> eventHistory, LocalDateTime createdAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.payload = payload;
        this.source = source;
        this.status = status;
        this.sagaType = sagaType;
        this.eventHistory = eventHistory;
        this.createdAt = createdAt;
    }

    public Event(String transactionId, LocalDateTime createdAt, Long orderId, Order payload, String sagaType) {
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.orderId = orderId;
        this.payload = payload;
        this.sagaType = sagaType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSagaType() {
        return sagaType;
    }

    public void setSagaType(String sagaType) {
        this.sagaType = sagaType;
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
