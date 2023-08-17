package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;

// The ReviewMapper interface defines methods for mapping between ReviewEntity, Review, and ReviewInput classes.
@Mapper(componentModel = "spring")
public interface ReviewMapper {

    // Method to convert a ReviewEntity object to a Review object
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "restaurant.city", target = "restaurantCity")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    Review toReview(ReviewEntity reviewEntity);

    // Method to convert a ReviewInput object to a ReviewEntity object
    ReviewEntity toReviewEntity(ReviewInput reviewInput);

    // Method to convert a ReviewEntity object to a ReviewInput object
    ReviewInput toReviewInput(ReviewEntity reviewEntity);
}