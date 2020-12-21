package com.wolox.training.exceptions.handler;

import com.wolox.training.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TrainingApplicationExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public GenericError objectNotFound(ObjectNotFoundException ex) {
        ErrorsEnum error = ErrorsEnum.OBJECT_NOT_FOUND;
        String message = new StringBuilder(ex.getObjectName()).append(" ")
                .append(error.getMessage())
                .toString();
        return new GenericError(error.getCode(), message);
    }

}
