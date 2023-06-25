package com.learning.product.exception;

public class ProductServiceBusinessException extends RuntimeException {

    public ProductServiceBusinessException(String message) {
        super(message);
    }
}
