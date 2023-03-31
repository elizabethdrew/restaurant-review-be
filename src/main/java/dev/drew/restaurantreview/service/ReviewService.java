package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.openapitools.model.Error;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drew.restaurantreview.util.SecurityUtils.getCurrentUserId;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    // Implement EntityUserIdProvider for ReviewEntity
    private final EntityUserIdProvider<ReviewEntity> reviewUserIdProvider = ReviewEntity::getUserId;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    // Add a new review to the database
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {

        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());
        Long currentUserId = getCurrentUserId();
        review.setUserId(currentUserId);

        ReviewResponse reviewResponse = new ReviewResponse();

        try {
            ReviewEntity savedReview = reviewRepository.save(review);
            Review savedApiReview = reviewMapper.toReview(savedReview);
            reviewResponse.setReview(savedApiReview);
            reviewResponse.setSuccess(true);
            return new ResponseEntity<>(reviewResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations, such as unique constraints
            reviewResponse.setSuccess(false);
            reviewResponse.setError(new Error().message("Invalid input: " + e.getMessage()));
            return new ResponseEntity<>(reviewResponse, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            reviewResponse.setSuccess(false);
            reviewResponse.setError(new Error().message("Database error: " + e.getMessage()));
            return new ResponseEntity<>(reviewResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle general exceptions
            reviewResponse.setSuccess(false);
            reviewResponse.setError(new Error().message("An unexpected error occurred: " + e.getMessage()));
            return new ResponseEntity<>(reviewResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Review>> getAllReviews(Long restaurantId, Long userId) {

        Stream<ReviewEntity> filteredEntities = reviewRepository.findAll().stream();

        if (restaurantId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getRestaurantId().equals(restaurantId));
        }

        if (userId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getUserId().equals(userId));
        }

        List<Review> reviews = filteredEntities
                .map(reviewMapper::toReview)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    public ResponseEntity<Review> getReviewById(Integer reviewId) {
        Optional<ReviewEntity> reviewEntityOptional = reviewRepository.findById(reviewId.longValue());

        if (reviewEntityOptional.isPresent()) {
            Review review = reviewMapper.toReview(reviewEntityOptional.get());
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
