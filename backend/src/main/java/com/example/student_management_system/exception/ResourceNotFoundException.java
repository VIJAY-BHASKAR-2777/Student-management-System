package com.example.student_management_system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom exception used when a requested resource (like a Student or Course)
 * is not found in the database.
 * The @ResponseStatus(HttpStatus.NOT_FOUND) annotation tells Spring to
 * automatically respond with a 404 Not Found status code if this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}