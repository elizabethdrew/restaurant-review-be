package dev.drew.restaurantreview.service;

import org.openapitools.model.PaginatedReviewResponse;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.UpdateReviewReplyRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

// ReviewService is an interface for managing reviews.
public interface ReviewService {

    // Add a new review
    Review addNewReview(ReviewInput reviewInput);

    // Get all reviews with optional filters (restaurantId, userId and rating)
    List<Review> getAllReviewsV1(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable);
    PaginatedReviewResponse getAllReviewsV2(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable);

    // Get a review by its ID
    Review getReviewById(Integer reviewId);

    // Update a review by its ID
    Review updateReviewById(Integer reviewId, ReviewInput reviewInput);

    // Delete a review by its ID
    void deleteReviewById(Integer reviewId);

    Review addReviewReply(Integer reviewId, UpdateReviewReplyRequest updateReviewReplyRequest);

    Review updateReviewReply(Integer reviewId, UpdateReviewReplyRequest updateReviewReplyRequest);

    Review deleteReviewReply(Integer reviewId);
}