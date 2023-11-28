package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import org.openapitools.api.ReviewsApi;
import org.openapitools.model.PaginatedReviewResponse;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.UpdateReviewReplyRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class ReviewController implements ReviewsApi {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/v1/reviews")
    @Override
    public ResponseEntity<Review> addNewReview(
            @RequestBody @Valid ReviewInput reviewInput) {
        Review review = reviewService.addNewReview(reviewInput);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/v1/reviews")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Review>> getAllReviewsV1(
            @Valid @RequestParam(value = "restaurant_id", required = false) Long restaurantId,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "rating", required = false) List<Integer> rating,
            @ParameterObject Pageable pageable){
        List<Review> reviews = reviewService.getAllReviewsV1(restaurantId,  userId, rating, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/v2/reviews")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PaginatedReviewResponse> getAllReviewsV2(
            @Valid @RequestParam(value = "restaurant_id", required = false) Long restaurantId,
            @Valid @RequestParam(value = "user_id", required = false) Long userId,
            @Valid @RequestParam(value = "rating", required = false) List<Integer> rating,
            @ParameterObject Pageable pageable){
        PaginatedReviewResponse reviews = reviewService.getAllReviewsV2(restaurantId,  userId, rating, pageable);
        return ResponseEntity.ok(reviews);
    }


    @Override
    @GetMapping("/v1/reviews/{reviewId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Review> getReviewById(
            @Min(1) @PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PutMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<Review> updateReviewById(
            @Min(1) @PathVariable Integer reviewId,
            @RequestBody @Valid ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.updateReviewById(reviewId, reviewInput);
        return ResponseEntity.ok(updatedReview);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @DeleteMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(
            @Min(1)  @PathVariable Integer reviewId) {
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PostMapping("/v1/reviews/{reviewId}/reply")
    public ResponseEntity<Review> addReviewReply(
            @Min(1) @PathVariable Integer reviewId,
            @RequestBody @Valid UpdateReviewReplyRequest updateReviewReplyRequest)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.addReviewReply(reviewId, updateReviewReplyRequest);
        return ResponseEntity.ok(updatedReview);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PutMapping("/v1/reviews/{reviewId}/reply")
    public ResponseEntity<Review> updateReviewReply(
            @Min(1) @PathVariable Integer reviewId,
            @RequestBody @Valid UpdateReviewReplyRequest updateReviewReplyRequest)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.updateReviewReply(reviewId, updateReviewReplyRequest);
        return ResponseEntity.ok(updatedReview);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @DeleteMapping("/v1/reviews/{reviewId}/reply")
    public ResponseEntity<Review> deleteReviewReply(
            @Min(1) @PathVariable Integer reviewId)
            throws ReviewNotFoundException, InsufficientPermissionException {
        Review updatedReview = reviewService.deleteReviewReply(reviewId);
        return ResponseEntity.ok(updatedReview);
    }
}
