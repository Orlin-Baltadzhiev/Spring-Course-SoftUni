package com.springdata.mapping.exception;

public class NoneExistingEntityException extends RuntimeException {
    public NoneExistingEntityException() {
    }

    public NoneExistingEntityException(String message) {
        super(message);
    }

    public NoneExistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneExistingEntityException(Throwable cause) {
        super(cause);
    }
}
