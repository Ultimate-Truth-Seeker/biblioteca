package com.example.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoanNotFoundException extends ResponseStatusException {
    public LoanNotFoundException(String id) {        super(HttpStatus.NOT_FOUND, "loan with ID: " + id + " not found");
    }
}
