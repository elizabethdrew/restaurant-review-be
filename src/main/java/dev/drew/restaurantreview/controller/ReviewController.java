package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping
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
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(
            @PathVariable Integer reviewId) {
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurant_id", required = false) Long restaurantId,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "rating", required = false) Integer rating,
            Pageable pageable
    ){
        List<Review> reviews = reviewService.getAllReviews(restaurantId,  userId, rating, pageable);
        return ResponseEntity.ok(reviews);
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
    public ResponseEntity<Review> getReviewById(@PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
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
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewById(
            @PathVariable Integer reviewId,
            @RequestBody @Valid ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.updateReviewById(reviewId, reviewInput);
        return ResponseEntity.ok(updatedReview);
    }
}
