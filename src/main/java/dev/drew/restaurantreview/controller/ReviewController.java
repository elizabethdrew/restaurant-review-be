package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@PreAuthorize("isAuthenticated()")
public class ReviewController implements ReviewsApi {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @Override
    public ResponseEntity<Review> addNewReview(
            @RequestBody @Valid ReviewInput reviewInput) {
        Review review = reviewService.addNewReview(reviewInput);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(
            @Min(1)  @PathVariable Integer reviewId) {
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Override
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviews(
            @Valid @RequestParam(value = "restaurant_id", required = false) Long restaurantId,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "rating", required = false) List<Integer> rating,
            @ParameterObject Pageable pageable){
        List<Review> reviews = reviewService.getAllReviews(restaurantId,  userId, rating, pageable);
        return ResponseEntity.ok(reviews);
    }


    @Override
    @GetMapping("/{reviewId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Review> getReviewById(
            @Min(1) @PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewById(
            @Min(1) @PathVariable Integer reviewId,
            @RequestBody @Valid ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.updateReviewById(reviewId, reviewInput);
        return ResponseEntity.ok(updatedReview);
    }
}
