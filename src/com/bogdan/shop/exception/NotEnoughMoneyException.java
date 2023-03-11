package com.bogdan.shop.exception;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super();
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
