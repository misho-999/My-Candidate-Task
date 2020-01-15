package com.candidate.task.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Person not found!")
public class PersonNotFoundException extends RuntimeException {
    private int statusCode;

    public PersonNotFoundException() {
        this.statusCode = 404;
    }

    public PersonNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
