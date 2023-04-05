package dev.drew.restaurantreview.service;

import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput);
    ResponseEntity<List<Review>> getAllReviews(Long restaurantId, Long userId);
    ResponseEntity<Review> getReviewById(Integer reviewId);
    ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput);
    ResponseEntity<Void> deleteReviewById(Integer reviewId);
}