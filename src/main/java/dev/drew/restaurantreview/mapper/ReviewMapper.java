package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewEntity reviewEntity);
    ReviewEntity toReviewEntity(ReviewInput reviewInput);
    ReviewInput toReviewInput(ReviewEntity reviewEntity);
}
