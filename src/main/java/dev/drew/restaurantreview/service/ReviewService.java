package dev.drew.restaurantreview.service;

import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

// ReviewService is an interface for managing reviews.
public interface ReviewService {

    // Add a new review
    ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput);

    // Get all reviews with optional filters (restaurantId and userId)
    ResponseEntity<List<Review>> getAllReviews(Long restaurantId, Long userId);

    // Get a review by its ID
    ResponseEntity<Review> getReviewById(Integer reviewId);

    // Update a review by its ID
    ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput);

    // Delete a review by its ID
    ResponseEntity<Void> deleteReviewById(Integer reviewId);
}