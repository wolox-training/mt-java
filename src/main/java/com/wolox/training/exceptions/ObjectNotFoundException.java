package com.wolox.training.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ObjectNotFoundException extends RuntimeException {

    private String objectName;

    public ObjectNotFoundException(String objectName) {
        this.objectName = objectName;
    }

}
