package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.ReviewService;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class ReviewController implements ReviewsApi {

    private final ReviewService reviewService;

    // Constructor for dependency injection of ReviewService
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Endpoint to add a new review, accessible to authenticated users
    @Override
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {
        return reviewService.addNewReview(reviewInput);
    }

    // Endpoint to delete a review by its ID, accessible to authenticated users
    @Override
    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }

    // Endpoint to get all reviews, accessible to all users
    // Supports optional filtering by restaurantId and userId
    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurantId", required = false) Long restaurantId,
            @Valid @RequestParam(value = "userId", required = false) Long userId) {
        return reviewService.getAllReviews(restaurantId, userId);
    }

    // Endpoint to get a single review by its ID, accessible to all users
    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<Review> getReviewById(Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    // Endpoint to update a review by its ID, accessible to authenticated users
    @Override
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        return reviewService.updateReviewById(reviewId, reviewInput);
    }
}

