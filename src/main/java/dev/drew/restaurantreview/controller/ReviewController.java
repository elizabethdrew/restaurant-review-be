package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Restaurant;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins ="http://localhost:3000")
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
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping("/review/add")
    @Override
    public ResponseEntity<Review> addNewReview(
            @RequestBody @Valid ReviewInput reviewInput) {
        Review review = reviewService.addNewReview(reviewInput);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    /**
     * Delete a review from the database by its ID.
     *
     * @param reviewId the ID of the review to delete
     * @return response entity indicating success or failure of the operation
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Override
    @DeleteMapping("/review/{reviewId}/delete")
    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }

    /**
     * Get all reviews from the database, with optional filtering by restaurantId and userId.
     *
     * @param restaurantId optional filter by restaurant ID
     * @param userId optional filter by user ID
     * @param rating optional filter by rating
     * @return response entity containing the list of reviews
     */
    @Override
    @GetMapping("/reviews")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurant_id", required = false) Long restaurantId,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "rating", required = false) Integer rating
    ){
        List<Review> reviews = reviewService.getAllReviews(restaurantId, rating, userId);
        return ResponseEntity.ok(reviews);
    }


    /**
     * Get a specific review by its ID.
     *
     * @param reviewId the ID of the review to retrieve
     * @return response entity containing the review data
     */
    @Override
    @GetMapping("/review/{reviewId}")
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
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Override
    @PutMapping("review/{reviewId}/edit")
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        return reviewService.updateReviewById(reviewId, reviewInput);
    }
}
