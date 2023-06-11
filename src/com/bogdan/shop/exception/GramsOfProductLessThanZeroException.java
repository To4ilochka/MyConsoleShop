package com.bogdan.shop.exception;

public class GramsOfProductLessThanZeroException extends RuntimeException {

    public GramsOfProductLessThanZeroException() {
        super();
    }

    public GramsOfProductLessThanZeroException(String message) {
        super(message);
    }
}
