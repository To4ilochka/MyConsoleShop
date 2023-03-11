package com.bogdan.shop.exception;

public class AmountOfProductLessThanZeroException extends Exception {
    public AmountOfProductLessThanZeroException() {
        super();
    }

    public AmountOfProductLessThanZeroException(String message) {
        super(message);
    }
}
