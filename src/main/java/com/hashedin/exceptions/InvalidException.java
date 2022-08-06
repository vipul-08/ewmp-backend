package com.hashedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidException extends RuntimeException {
    private static final long serialVersionUID = -3368655266237942363L;

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
