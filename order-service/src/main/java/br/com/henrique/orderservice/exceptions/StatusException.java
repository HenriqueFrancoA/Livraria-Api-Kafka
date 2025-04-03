package br.com.henrique.orderservice.exceptions;

public class StatusException extends RuntimeException {
    public StatusException(String message) {
        super(message);
    }
}
