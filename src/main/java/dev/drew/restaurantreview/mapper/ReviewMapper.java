package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;

// The ReviewMapper interface defines methods for mapping between ReviewEntity, Review, and ReviewInput classes.
@Mapper(componentModel = "spring")
public interface ReviewMapper {

    // Method to convert a ReviewEntity object to a Review object
    Review toReview(ReviewEntity reviewEntity);

    // Method to convert a ReviewInput object to a ReviewEntity object
    ReviewEntity toReviewEntity(ReviewInput reviewInput);

    // Method to convert a ReviewEntity object to a ReviewInput object
    ReviewInput toReviewInput(ReviewEntity reviewEntity);
}
