package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleUserNotFoundException() {
        ResponseEntity<ErrorResponse> response = handler.handleUserNotFoundException(new UserNotFoundException("User Not Found"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleRestaurantNotFoundException() {
        ResponseEntity<ErrorResponse> response = handler.handleRestaurantNotFoundException(new RestaurantNotFoundException("Restaurant Not Found"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleReviewNotFoundException() {
        ResponseEntity<ErrorResponse> response = handler.handleReviewNotFoundException(new ReviewNotFoundException("Review Not Found"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleInsufficientPermissionException() {
        ResponseEntity<ErrorResponse> response = handler.handleInsufficientPermissionException(new InsufficientPermissionException());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleDataIntegrityViolationException() {
        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleDataAccessException() {
        ResponseEntity<ErrorResponse> response = handler.handleDataAccessException(new EmptyResultDataAccessException(0));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleGeneralException() {
        ResponseEntity<ErrorResponse> response = handler.handleGeneralException(new Exception());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getCode());
    }
}
