package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.ReviewEntity;
import javax.annotation.processing.Generated;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-05T10:53:03+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toReview(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewEntity.getId() );
        review.setRestaurantId( reviewEntity.getRestaurantId() );
        review.setUserId( reviewEntity.getUserId() );
        review.setRating( reviewEntity.getRating() );
        review.setComment( reviewEntity.getComment() );
        review.setCreatedAt( reviewEntity.getCreatedAt() );
        review.setUpdatedAt( reviewEntity.getUpdatedAt() );

        return review;
    }

    @Override
    public ReviewEntity toReviewEntity(ReviewInput reviewInput) {
        if ( reviewInput == null ) {
            return null;
        }

        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setRestaurantId( reviewInput.getRestaurantId() );
        reviewEntity.setRating( reviewInput.getRating() );
        reviewEntity.setComment( reviewInput.getComment() );

        return reviewEntity;
    }

    @Override
    public ReviewInput toReviewInput(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        ReviewInput reviewInput = new ReviewInput();

        reviewInput.setRestaurantId( reviewEntity.getRestaurantId() );
        reviewInput.setRating( reviewEntity.getRating() );
        reviewInput.setComment( reviewEntity.getComment() );

        return reviewInput;
    }
}
