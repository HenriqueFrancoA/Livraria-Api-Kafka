package br.com.henrique.paymentservice.exceptions;

public class StatusException extends RuntimeException {
    public StatusException(String message) {
        super(message);
    }
}
