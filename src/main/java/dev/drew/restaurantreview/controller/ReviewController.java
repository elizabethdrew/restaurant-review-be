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

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {
        return reviewService.addNewReview(reviewInput);
    }

    @Override
    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurantId", required = false) Long restaurantId,
            @Valid @RequestParam(value = "userId", required = false) Long userId) {
        return reviewService.getAllReviews(restaurantId, userId);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<Review> getReviewById(Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @Override
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        return reviewService.updateReviewById(reviewId, reviewInput);
    }
}
