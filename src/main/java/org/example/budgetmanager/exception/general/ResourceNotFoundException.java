package org.example.budgetmanager.exception.general;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
        System.out.println("HERE !!");
    }
}
