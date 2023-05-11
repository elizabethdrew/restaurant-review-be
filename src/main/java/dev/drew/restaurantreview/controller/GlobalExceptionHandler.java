package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle RestaurantNotFoundException
    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        logger.warn("Restaurant not found error", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Handle InsufficientPermissionException
    @ExceptionHandler(InsufficientPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleInsufficientPermissionException(InsufficientPermissionException e) {
        logger.warn("Insufficient permission error", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // Handle DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logger.warn("Data integrity error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // Handle DataAccessException
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        logger.warn("Data access error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Handle other general exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        logger.warn("An unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
