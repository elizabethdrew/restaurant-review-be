package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.openapitools.model.Error;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drew.restaurantreview.util.SecurityUtils.getCurrentUserId;
import static dev.drew.restaurantreview.util.SecurityUtils.isAdminOrOwner;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    private final RestaurantRepository restaurantRepository;

    // Implement EntityUserIdProvider for ReviewEntity
    private final EntityUserIdProvider<ReviewEntity> reviewUserIdProvider = ReviewEntity::getUserId;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.restaurantRepository = restaurantRepository;
    }

    // Add a new review to the database
    public ResponseEntity<ReviewResponse> addNewReview(ReviewInput reviewInput) {
        Long currentUserId = getCurrentUserId();

        // Check if restaurant exists
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(reviewInput.getRestaurantId());
        if(!restaurantEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ReviewResponse()
                            .success(false)
                            .error(new Error().message("Invalid Restaurant ID")));
        }

        // Check if the user has already reviewed the restaurant within the last year
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);
        List<ReviewEntity> existingReviews = reviewRepository.findByUserIdAndRestaurantId(currentUserId, reviewInput.getRestaurantId());

        boolean hasReviewWithinOneYear = existingReviews.stream()
                .anyMatch(review -> review.getCreatedAt().isAfter(oneYearAgo));

        if (hasReviewWithinOneYear) {
            // Return error response if a review within the last year exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Add the new review
        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());
        review.setUserId(currentUserId);

        ReviewResponse reviewResponse = new ReviewResponse();

        try {
            ReviewEntity savedReview = reviewRepository.save(review);
            Review savedApiReview = reviewMapper.toReview(savedReview);

            // Update the restaurant rating
            updateRestaurantRating(savedReview.getRestaurantId());

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

    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        Optional<ReviewEntity> reviewEntityOptional = reviewRepository.findById(reviewId.longValue());

        if (reviewEntityOptional.isPresent()) {
            ReviewEntity reviewEntity = reviewEntityOptional.get();

            if (!isAdminOrOwner(reviewEntity, ReviewEntity::getUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            ReviewEntity updatedEntity = reviewMapper.toReviewEntity(reviewInput);
            updatedEntity.setId(reviewEntity.getId());
            updatedEntity.setCreatedAt(reviewEntity.getCreatedAt());
            updatedEntity.setUserId(reviewEntity.getUserId());
            updatedEntity.setRestaurantId(reviewEntity.getRestaurantId());
            updatedEntity.setUpdatedAt(OffsetDateTime.now());

            try {
                ReviewEntity savedReview = reviewRepository.save(updatedEntity);
                Review savedApiReview = reviewMapper.toReview(savedReview);

                // Update the restaurant rating
                updateRestaurantRating(savedReview.getRestaurantId());

                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setReview(savedApiReview);

                return ResponseEntity.ok(reviewResponse);
            } catch (DataIntegrityViolationException e) {
                // Handle database constraint violations, such as unique constraints
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (DataAccessException e) {
                // Handle other database-related exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (Exception e) {
                // Handle general exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Void> deleteReviewById(Integer reviewId) {
        Optional<ReviewEntity> reviewEntityOptional = reviewRepository.findById(reviewId.longValue());

        if (reviewEntityOptional.isPresent()) {
            ReviewEntity reviewEntity = reviewEntityOptional.get();

            // Check if the current user is an admin or the owner of the review
            if (!isAdminOrOwner(reviewEntity, ReviewEntity::getUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Save restaurant ID before deleting review
            Long restaurantId = reviewEntity.getRestaurantId();

            // Delete review
            reviewRepository.deleteById(reviewId.longValue());

            // Update restaurant rating
            updateRestaurantRating(restaurantId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public void updateRestaurantRating(Long restaurantId) {
        List<ReviewEntity> reviews = reviewRepository.findByRestaurantId(restaurantId);

        if (!reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .mapToDouble(ReviewEntity::getRating)
                    .average()
                    .orElse(0);

            // Round the average rating to the nearest integer
            int roundedAverageRating = (int) Math.round(averageRating);


            RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).orElse(null);
            if (restaurantEntity != null) {
                restaurantEntity.setRating(roundedAverageRating);
                restaurantRepository.save(restaurantEntity);
            }
        }
    }
}
