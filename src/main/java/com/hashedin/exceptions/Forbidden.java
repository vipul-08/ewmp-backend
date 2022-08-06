package com.hashedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends RuntimeException
{
    private static final long serialVersionUID = -3368655266237942363L;

    public Forbidden(String message) {
        super(message);
    }

    public Forbidden(Throwable cause) {
        super(cause);
    }

    public Forbidden(String message, Throwable cause) {
        super(message, cause);
    }
}
