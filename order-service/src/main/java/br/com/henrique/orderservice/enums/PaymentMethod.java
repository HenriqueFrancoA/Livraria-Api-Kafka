package br.com.henrique.orderservice.enums;

public enum PaymentMethod {
    CREDIT(1, "CRÉDITO"),
    DEBIT(2, "DÉBITO"),
    TICKET(3, "BOLETO"),
    PIX(4, "PIX");

    private final int code;
    private final String description;

    PaymentMethod(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}