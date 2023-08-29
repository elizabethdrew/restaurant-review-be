package dev.drew.restaurantreview.exception;

public class UserOwnsRestaurantException extends RuntimeException {
    public UserOwnsRestaurantException(String message) {
        super(message);
    }
}
