package com.retail.product_catalog.exception;

public class InvalidSearchParameterException extends RuntimeException {
    public InvalidSearchParameterException(String message) {
        super(message);
    }
}
