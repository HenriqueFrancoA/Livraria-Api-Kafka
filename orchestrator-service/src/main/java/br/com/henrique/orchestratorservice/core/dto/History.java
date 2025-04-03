package br.com.henrique.orchestratorservice.core.dto;


import br.com.henrique.orchestratorservice.core.enums.EEventSource;
import br.com.henrique.orchestratorservice.core.enums.ESagaStatus;

import java.time.LocalDateTime;

public class History {

    private Long id;
    private EEventSource source;
    private ESagaStatus status;
    private String message;
    private LocalDateTime createdAt;

    public History() {
    }

    public History(Long id, EEventSource source, ESagaStatus status, String message, LocalDateTime createdAt) {
        this.id = id;
        this.source = source;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public History(EEventSource source, ESagaStatus status, String message, LocalDateTime createdAt) {
        this.source = source;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EEventSource getSource() {
        return source;
    }

    public void setSource(EEventSource source) {
        this.source = source;
    }

    public ESagaStatus getStatus() {
        return status;
    }

    public void setStatus(ESagaStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
