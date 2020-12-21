package com.wolox.training.exceptions.handler;

public enum ErrorsEnum {
    OBJECT_NOT_FOUND("001", "not found"),
    BOOK_ALREADY_OWNED("002", "Book already owned");

    private final String code;
    private final String message;

    ErrorsEnum(String code, String message) {
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
