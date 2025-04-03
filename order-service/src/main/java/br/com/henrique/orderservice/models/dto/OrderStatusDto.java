package br.com.henrique.orderservice.models.dto;

public class OrderStatusDto {

    private String status;

    public OrderStatusDto() {
    }

    public OrderStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
