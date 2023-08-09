package dev.drew.restaurantreview.exception;

public class DuplicateCuisineException extends RuntimeException {
    public DuplicateCuisineException(String cuisineName) {
        super(cuisineName);
    }

}
