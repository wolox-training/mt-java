package com.wolox.training.exceptions.handler;

public class GenericError {

    private final String code;
    private final String message;

    public GenericError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
