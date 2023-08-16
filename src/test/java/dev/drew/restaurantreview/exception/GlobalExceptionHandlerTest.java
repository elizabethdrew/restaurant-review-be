package dev.drew.restaurantreview.exception;

import dev.drew.restaurantreview.model.ErrorResponse;
import org.junit.jupiter.api.Test;
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
        assertEquals("Bad Request", response.getBody().getMessage());
    }

    @Test
    void shouldHandleDataAccessException() {
        ResponseEntity<ErrorResponse> response = handler.handleDataAccessException(new EmptyResultDataAccessException(0));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getCode());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }

    @Test
    void shouldHandleGeneralException() {
        ResponseEntity<ErrorResponse> response = handler.handleGeneralException(new Exception());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleCuisineNotFoundException() {
        String cuisineName = "Italian";
        ResponseEntity<ErrorResponse> response = handler.handleCuisineNotFoundException(new CuisineNotFoundException(cuisineName));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleDuplicateCuisineException() {
        String cuisineName = "Italian";
        ResponseEntity<ErrorResponse> response = handler.handleDuplicateCuisineException(new DuplicateCuisineException(cuisineName));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getCode());
    }

    @Test
    void shouldHandleCuisineReferencedByRestaurantException() {
        String cuisineName = "Italian";
        ResponseEntity<ErrorResponse> response = handler.handleCuisineReferencedByRestaurantException(new CuisineReferencedByRestaurantException(cuisineName));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getCode());
    }
}
