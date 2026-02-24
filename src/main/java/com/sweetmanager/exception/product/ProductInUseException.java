package com.sweetmanager.exception.product;

public class ProductInUseException extends RuntimeException {

    public ProductInUseException(String message) {
        super(message);
    }
}
