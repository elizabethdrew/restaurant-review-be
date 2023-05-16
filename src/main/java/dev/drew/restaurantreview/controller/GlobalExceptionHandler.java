package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("Not Found");
        log.warn("User not found error", e);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle RestaurantNotFoundException
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("Not Found");
        log.warn("Restaurant not found error", e);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle InsufficientPermissionException
    @ExceptionHandler(InsufficientPermissionException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPermissionException(InsufficientPermissionException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.FORBIDDEN.value());
        error.setMessage("Forbidden");
        log.warn("Insufficient permission error", e);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Handle DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Bad request");
        log.warn("Data integrity error", e);
        return new ResponseEntity <>( error, HttpStatus.BAD_REQUEST);
    }

    // Handle DataAccessException
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Internal Server Error");
        log.warn("Data access error", e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle other general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Internal Server Error");
        log.warn("An unexpected error occurred", e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
