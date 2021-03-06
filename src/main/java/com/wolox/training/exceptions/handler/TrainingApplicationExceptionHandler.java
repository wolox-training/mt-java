package com.wolox.training.exceptions.handler;

import com.wolox.training.exceptions.BookAlreadyOwnedException;
import com.wolox.training.exceptions.MyJsonProcessingException;
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

    @ExceptionHandler({BookAlreadyOwnedException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public GenericError bookAlreadyOwned(BookAlreadyOwnedException ex) {
        ErrorsEnum error = ErrorsEnum.BOOK_ALREADY_OWNED;
        return new GenericError(error.getCode(), error.getMessage());
    }

    @ExceptionHandler({MyJsonProcessingException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public GenericError jsonProcessingException(MyJsonProcessingException ex) {
        ErrorsEnum error = ErrorsEnum.JSON_PROCESSING_EXCEPTION;
        return new GenericError(error.getCode(), ex.getError());
    }

}
