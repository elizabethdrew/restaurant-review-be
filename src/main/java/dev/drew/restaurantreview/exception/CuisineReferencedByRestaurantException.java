package dev.drew.restaurantreview.exception;

public class CuisineReferencedByRestaurantException extends RuntimeException {
    public CuisineReferencedByRestaurantException(String cuisineName) {
        super(cuisineName);
    }

}
