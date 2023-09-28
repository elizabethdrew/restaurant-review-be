package dev.drew.restaurantreview.exception;

public class NotRestaurantOwnerException extends RuntimeException {
    public NotRestaurantOwnerException(String message) {
        super(message);
    }
}
