package com.example.student_management_system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * =================================================================
 * Global Exception Handler
 * =================================================================
 * Purpose:
 * This class acts as a central point for handling exceptions across the entire application.
 *
 * Handled Exceptions and Output Formats:
 *
 * 1. ResourceNotFoundException (HTTP 404 NOT FOUND)
 * - Triggered: When a specific resource (e.g., a student with a given ID) is not found.
 * - JSON Output Format:
 * {
 * "timestamp": "Fri Aug 29 18:35:02 PDT 2025",
 * "message": "Student not found with id: 99"
 * }
 *
 * 2. MethodArgumentNotValidException (HTTP 400 BAD REQUEST)
 * - Triggered: When input validation fails (e.g., an email is malformed or a required field is null).
 * - JSON Output Format:
 * {
 * "firstName": "First name cannot be empty",
 * "email": "Email should be valid"
 * }
 *
 * 3. Exception (Generic Catch-all) (HTTP 500 INTERNAL SERVER ERROR)
 * - Triggered: For any other unexpected server-side error.
 * - JSON Output Format:
 * {
 * "timestamp": "Fri Aug 29 18:35:02 PDT 2025",
 * "message": "An internal server error occurred."
 * }
 * =================================================================
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     * This is triggered when a specific resource (e.g., a student or course) is not found.
     * @param ex The caught ResourceNotFoundException.
     * @param request The current web request.
     * @return A ResponseEntity with a custom error message and a 404 NOT FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles MethodArgumentNotValidException.
     * This exception is thrown by Spring when the @Valid annotation fails on a controller method's parameter.
     * For example, if an email is invalid or a required field is empty.
     * @param ex The caught MethodArgumentNotValidException.
     * @return A ResponseEntity containing a map of field names to error messages and a 400 BAD REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions.
     * This is a fallback handler for any unexpected errors, preventing stack traces from being exposed.
     * @param ex The caught generic Exception.
     * @param request The current web request.
     * @return A ResponseEntity with a generic error message and a 500 INTERNAL SERVER ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        // It's good practice to log the full exception here for debugging purposes.
        // e.g., log.error("An unexpected error occurred: ", ex);
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("message", "An internal server error occurred.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}