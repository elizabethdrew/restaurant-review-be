package dev.drew.restaurantreview.service;

import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.data.domain.Pageable;

import java.util.List;

// ReviewService is an interface for managing reviews.
public interface ReviewService {

    // Add a new review
    Review addNewReview(ReviewInput reviewInput);

    // Get all reviews with optional filters (restaurantId, userId and rating)
    List<Review> getAllReviews(Long restaurantId, Long userId, Integer rating, Pageable pageable);

    // Get a review by its ID
    Review getReviewById(Integer reviewId);

    // Update a review by its ID
    Review updateReviewById(Integer reviewId, ReviewInput reviewInput);

    // Delete a review by its ID
    void deleteReviewById(Integer reviewId);
}