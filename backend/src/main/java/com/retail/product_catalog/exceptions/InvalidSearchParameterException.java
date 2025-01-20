package com.retail.product_catalog.exceptions;

public class InvalidSearchParameterException extends RuntimeException {
    public InvalidSearchParameterException(String message) {
        super(message);
    }
}
