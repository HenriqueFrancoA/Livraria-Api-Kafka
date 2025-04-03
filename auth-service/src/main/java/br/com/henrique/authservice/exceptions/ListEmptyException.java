package br.com.henrique.authservice.exceptions;

import java.util.NoSuchElementException;

public class ListEmptyException extends NoSuchElementException {
    public ListEmptyException(String message) {
        super(message);
    }
}
