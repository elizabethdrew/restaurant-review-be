package dev.drew.restaurantreview.exception;

public class DuplicateRestaurantException extends RuntimeException {
    public DuplicateRestaurantException(String name, String city) {
        super(name + ", " + city);
    }
}
