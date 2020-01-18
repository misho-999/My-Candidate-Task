package com.candidate.task.errors;

import com.candidate.task.constants.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionConstants.MISSING_PERSON_ID_REASON)
public class MissingPersonException extends RuntimeException {
    private int statusCode;

    public MissingPersonException() {
        this.statusCode = 404;
    }

    public MissingPersonException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
