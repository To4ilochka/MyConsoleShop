package com.bogdan.shop.exception;

//TODO change to RunTimeException
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super();
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
