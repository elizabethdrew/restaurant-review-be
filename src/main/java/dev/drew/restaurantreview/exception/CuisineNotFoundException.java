package dev.drew.restaurantreview.exception;

public class CuisineNotFoundException extends RuntimeException {
    public CuisineNotFoundException(String cuisineName) {
        super(cuisineName);
    }

}
