package dev.drew.restaurantreview.controller;

import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class ReviewController implements ReviewsApi {
    @Override
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Review>> getAllReviews(Long restaurantId, Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<Review> getReviewById(Integer reviewId) {
        return null;
    }

    @Override
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        return null;
    }
}
