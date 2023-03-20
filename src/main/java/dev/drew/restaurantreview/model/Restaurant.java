package dev.drew.restaurantreview.model;

public record Restaurant(
        int id,
        String name,
        String city,
        int rating
) {
}
