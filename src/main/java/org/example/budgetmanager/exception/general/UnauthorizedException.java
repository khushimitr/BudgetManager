package org.example.budgetmanager.exception.general;

import org.springframework.http.HttpStatus;


public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
