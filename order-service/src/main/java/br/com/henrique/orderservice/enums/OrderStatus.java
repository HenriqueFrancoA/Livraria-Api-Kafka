package br.com.henrique.orderservice.enums;

public enum OrderStatus {
    PENDING(1, "PENDENTE"),
    APPROVED(2, "APROVADO"),
    REJECTED(3, "NEGADO"),
    CANCELED(4, "CANCELADO");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
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