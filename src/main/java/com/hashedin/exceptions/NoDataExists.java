package com.hashedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoDataExists extends RuntimeException
{
    private static final long serialVersionUID = -3368655266237942363L;

    public NoDataExists(String message) {
        super(message);
    }

    public NoDataExists(Throwable cause) {
        super(cause);
    }

    public NoDataExists(String message, Throwable cause) {
        super(message, cause);
    }
}