package com.hashedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataExistsException extends RuntimeException
{
    private static final long serialVersionUID = -3368655266237942363L;

    public DataExistsException(String message) {
        super(message);
    }

    public DataExistsException(Throwable cause) {
        super(cause);
    }

    public DataExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}