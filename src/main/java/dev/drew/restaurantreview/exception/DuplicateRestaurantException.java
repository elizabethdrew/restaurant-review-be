package dev.drew.restaurantreview.exception;

public class DuplicateRestaurantException extends RuntimeException {
    public DuplicateRestaurantException(String message) {
        super(message);
    }
}
