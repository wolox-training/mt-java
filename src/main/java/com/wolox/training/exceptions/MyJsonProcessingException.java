package com.wolox.training.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class MyJsonProcessingException extends RuntimeException{

    private String error;

    public MyJsonProcessingException(String error) {
        this.error = error;
    }

}
