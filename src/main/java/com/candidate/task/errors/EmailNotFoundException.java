package com.candidate.task.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email not found!")
public class EmailNotFoundException extends RuntimeException {
    private int statusCode;

    public EmailNotFoundException() {
        this.statusCode = 404;
    }

    public EmailNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
