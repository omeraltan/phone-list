package com.phone.api.exception;

public class CustomerAmountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerAmountException(String message) {
        super(message);
    }
}
