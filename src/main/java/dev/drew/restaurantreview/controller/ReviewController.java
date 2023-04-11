package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.ReviewService;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@PreAuthorize("isAuthenticated()")
public class ReviewController implements ReviewsApi {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Add a new review to the database.
     *
     * @param reviewInput input data for the new review
     * @return response entity containing the new review data
     */
    @PostMapping
    @Override
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {
        return reviewService.addNewReview(reviewInput);
    }

    /**
     * Delete a review from the database by its ID.
     *
     * @param reviewId the ID of the review to delete
     * @return response entity indicating success or failure of the operation
     */
    @Override
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }

    /**
     * Get all reviews from the database, with optional filtering by restaurantId and userId.
     *
     * @param restaurantId optional filter by restaurant ID
     * @param userId optional filter by user ID
     * @return response entity containing the list of reviews
     */
    @Override
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurantId", required = false) Long restaurantId,
            @Valid @RequestParam(value = "userId", required = false) Long userId) {
        return reviewService.getAllReviews(restaurantId, userId);
    }

    /**
     * Get a specific review by its ID.
     *
     * @param reviewId the ID of the review to retrieve
     * @return response entity containing the review data
     */
    @Override
    @GetMapping("/{reviewId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Review> getReviewById(Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    /**
     * Update a review's data by its ID.
     *
     * @param reviewId the ID of the review to update
     * @param reviewInput the updated review data
     * @return response entity containing the updated review data
     */
    @Override
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        return reviewService.updateReviewById(reviewId, reviewInput);
    }
}
