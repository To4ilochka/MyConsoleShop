package com.bogdan.shop.exception;

public class AmountOfProductLessThanZeroException extends RuntimeException {
    public AmountOfProductLessThanZeroException() {
        super();
    }

    public AmountOfProductLessThanZeroException(String message) {
        super(message);
    }
}
