package com.avanade.invoice.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}