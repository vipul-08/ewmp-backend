package com.hashedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Unauthorized  extends RuntimeException{
    private static final long serialVersionUID = -3368655266237942363L;

    public Unauthorized(String message) {
        super(message);
    }

    public Unauthorized(Throwable cause) {
        super(cause);
    }

    public Unauthorized(String message, Throwable cause) {
        super(message, cause);
    }
}
