package com.example.PaymentGatewaySimulator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.
                                        getField(),
                                error.getDefaultMessage()
                        )
                );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePaymentNotFound(PaymentNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPaymentStateException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPaymentState(InvalidPaymentStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RefundNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleRefundNotAllowed(
            RefundNotAllowedException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(
                error,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IdempotencyConflictException.class)
    public ResponseEntity<Map<String, Object>> handleIdempotencyConflict(
            IdempotencyConflictException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", 409);
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}



