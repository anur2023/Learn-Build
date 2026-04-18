package com.doctor.appointment.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    // ─────────────────────────────────────────────
    // 400 - Bad Request
    // ─────────────────────────────────────────────

    // Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 400);
        body.put("error", "Validation Failed");
        body.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Wrong type in path variable or request param
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        return buildResponse(HttpStatus.BAD_REQUEST, "Type Mismatch", message);
    }

    // Illegal argument (e.g., invalid enum value)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Argument", ex.getMessage());
    }

    // ─────────────────────────────────────────────
    // 401 - Unauthorized
    // ─────────────────────────────────────────────

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", "Invalid email or password");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Map<String, Object>> handleDisabledUser(DisabledException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", "Account is disabled");
    }

    // ─────────────────────────────────────────────
    // 403 - Forbidden
    // ─────────────────────────────────────────────

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Forbidden", "You do not have permission to access this resource");
    }

    // ─────────────────────────────────────────────
    // 404 - Not Found
    // ─────────────────────────────────────────────

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
    }

    // Generic not found (used across all modules)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        // Route to 404 if message indicates not found
        if (message != null && message.toLowerCase().contains("not found")) {
            return buildResponse(HttpStatus.NOT_FOUND, "Not Found", message);
        }

        // Route to 409 if message indicates conflict/duplicate
        if (message != null && (message.toLowerCase().contains("already exists")
                || message.toLowerCase().contains("already registered")
                || message.toLowerCase().contains("already booked"))) {
            return buildResponse(HttpStatus.CONFLICT, "Conflict", message);
        }

        // Route to 400 for business rule violations
        if (message != null && (message.toLowerCase().contains("invalid")
                || message.toLowerCase().contains("cannot")
                || message.toLowerCase().contains("not allowed"))) {
            return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
        }

        // Default 500
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", message);
    }

    // ─────────────────────────────────────────────
    // 409 - Conflict
    // ─────────────────────────────────────────────

    // Double booking, duplicate slot, etc.
    // Handled inside RuntimeException block above via message keyword matching

    // ─────────────────────────────────────────────
    // 500 - Internal Server Error (catch-all)
    // ─────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "Something went wrong. Please try again later.");
    }
}