package dev.drew.restaurantreview.exception;

public class CuisineNotFoundException extends RuntimeException {
    public CuisineNotFoundException(String cuisineName) {
        super("Cuisine not found with the name" + cuisineName);
    }

}
