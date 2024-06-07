package com.example.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {
    public BookNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "book with ID: " + id + " not found");
    }

}
