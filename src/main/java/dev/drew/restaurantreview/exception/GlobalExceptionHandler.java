package dev.drew.restaurantreview.exception;

import dev.drew.restaurantreview.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
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

    private ResponseEntity<ErrorResponse> generateErrorResponse(HttpStatus status, String message, Exception e) {
        log.warn(message, e);
        ErrorResponse error = new ErrorResponse();
        error.setCode(status.value());
        error.setMessage(message);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReviewException(DuplicateReviewException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Restaurant already reviewed by user within last year.", e);
    }

    @ExceptionHandler(UserOwnsRestaurantException.class)
    public ResponseEntity<ErrorResponse> handleUserOwnsRestaurantException(UserOwnsRestaurantException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "User owns Restaurant", e);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Constraint Violation", e);
    }

    @ExceptionHandler(NoResultsFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResultsFoundException(NoResultsFoundException e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "No Results Found", e);
    }

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStateException(InvalidStateException e) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid State", e);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Credentials", e);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication Failed", e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "User not found", e);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Restaurant not found", e);
    }

    @ExceptionHandler(DuplicateRestaurantException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRestaurantException(DuplicateRestaurantException e) {
        return generateErrorResponse(HttpStatus.CONFLICT, "Restaurant already exists", e);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Input", e);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Review not found", e);
    }

    @ExceptionHandler(CuisineNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCuisineNotFoundException(CuisineNotFoundException e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Cuisine not found", e);
    }

    @ExceptionHandler(DuplicateCuisineException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCuisineException(DuplicateCuisineException e) {
        return generateErrorResponse(HttpStatus.CONFLICT, "Cuisine already exists", e);
    }

    @ExceptionHandler(CuisineReferencedByRestaurantException.class)
    public ResponseEntity<ErrorResponse> handleCuisineReferencedByRestaurantException(CuisineReferencedByRestaurantException e) {
        return generateErrorResponse(HttpStatus.CONFLICT, "Cuisine Referenced By Restaurants", e);
    }

    @ExceptionHandler(InsufficientPermissionException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPermissionException(InsufficientPermissionException e) {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "Insufficient Permissions", e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", e);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", e);
    }
}
