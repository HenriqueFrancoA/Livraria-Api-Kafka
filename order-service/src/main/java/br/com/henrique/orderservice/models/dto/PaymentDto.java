package br.com.henrique.orderservice.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class PaymentDto{

    private OrderDto order;
    private String paymentMethod;
    private BigDecimal value;
    private LocalDateTime paymentDate;
    private String status;

    public PaymentDto() {
    }

    public PaymentDto(OrderDto order, String paymentMethod, BigDecimal value, LocalDateTime paymentDate, String status) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.value = value;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public OrderDto getOrder() {return order;}

    public void setOrder(OrderDto order) {this.order = order;}

    public String getPaymentMethod() {return paymentMethod;}

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
