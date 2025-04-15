package org.example.budgetmanager.exception.general;

import org.springframework.http.HttpStatus;


public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
