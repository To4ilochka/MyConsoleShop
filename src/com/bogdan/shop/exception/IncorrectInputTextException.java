package com.bogdan.shop.exception;

public class IncorrectInputTextException extends RuntimeException {

    public IncorrectInputTextException() {
        super();
    }

    public IncorrectInputTextException(String message) {
        super(message);
    }
}
