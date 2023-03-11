package com.bogdan.shop.exception;

public class GramsOfProductLessThanZeroException extends Exception {

    public GramsOfProductLessThanZeroException() {
        super();
    }

    public GramsOfProductLessThanZeroException(String message) {
        super(message);
    }
}
