package com.candidate.task.errors;

import com.candidate.task.constants.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionConstants.EMPTY_SEARCH_REASON)
public class SearchBoxException extends RuntimeException {
    private int statusCode;

    public SearchBoxException() {
        this.statusCode = 404;
    }

    public SearchBoxException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
