package br.com.henrique.paymentservice.models.dto;

public class PaymentStatusDto {

    private String status;

    public PaymentStatusDto() {
    }

    public PaymentStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
