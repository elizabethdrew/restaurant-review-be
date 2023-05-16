package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.ReviewService;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Error;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.ReviewResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drew.restaurantreview.util.SecurityUtils.getCurrentUserId;
import static dev.drew.restaurantreview.util.SecurityUtils.isAdminOrOwner;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    private final RestaurantRepository restaurantRepository;

    // Implement EntityUserIdProvider for ReviewEntity
    private final EntityUserIdProvider<ReviewEntity> reviewUserIdProvider = ReviewEntity::getUserId;
    private UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    // Add a new review to the database
    @Transactional
    public Review addNewReview(ReviewInput reviewInput) {

        // Fetch the restaurant entity
        RestaurantEntity currentRestaurant = restaurantRepository.findById(reviewInput.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the user has already reviewed the restaurant within the last year
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);
        List<ReviewEntity> existingReviews = reviewRepository.findByUser_IdAndRestaurant_Id(currentUserId, reviewInput.getRestaurantId());

        boolean hasReviewWithinOneYear = existingReviews.stream()
                .anyMatch(review -> review.getCreatedAt().isAfter(oneYearAgo));

        if (hasReviewWithinOneYear) {
            // Return error response if a review within the last year exists
            throw new IllegalArgumentException("User already reviewed")
        }

        // Add the new review
        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());

        // Set the restaurant and user entities
        review.setRestaurant(currentRestaurant);
        review.setUser(currentUser);

        ReviewResponse reviewResponse = new ReviewResponse();

        ReviewEntity savedReview = reviewRepository.save(review);
        Review savedApiReview = reviewMapper.toReview(savedReview);

        // Update the restaurant rating
        updateRestaurantRating(savedReview.getRestaurantId());

        return savedApiReview;
    }

    public List<Review> getAllReviews(Long restaurantId, Long userId, Integer rating) {

        Stream<ReviewEntity> filteredEntities = reviewRepository.findAll().stream();

        if (restaurantId != null) {
            filteredEntities = filteredEntities
                    .filter(r -> r.getRestaurant().getId().equals(restaurantId));
        }

        if (rating != null) {
            filteredEntities = filteredEntities
                    .filter(r -> r.getRating().equals(rating));
        }

        if (userId != null) {
            filteredEntities = filteredEntities.filter(r -> r.getUser().getId().equals(userId));
        }

        return filteredEntities.map(reviewMapper::toReview).collect(Collectors.toList());
    }

    public Review getReviewById(Integer reviewId) throws ReviewNotFoundException {
        return reviewRepository.findById(reviewId.longValue())
            .map(reviewMapper::toReview)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));
    }

    @Transactional
    public ResponseEntity<ReviewResponse> updateReviewById(Integer reviewId, ReviewInput reviewInput) {
        Optional<ReviewEntity> reviewEntityOptional = reviewRepository.findById(reviewId.longValue());

        if (reviewEntityOptional.isPresent()) {
            ReviewEntity reviewEntity = reviewEntityOptional.get();

            if (!isAdminOrOwner(reviewEntity, ReviewEntity::getUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Manually set the updated properties from the ReviewInput object
            reviewEntity.setRating(reviewInput.getRating());
            reviewEntity.setComment(reviewInput.getComment());
            reviewEntity.setUpdatedAt(OffsetDateTime.now());

            try {
                ReviewEntity savedReview = reviewRepository.save(reviewEntity);
                Review savedApiReview = reviewMapper.toReview(savedReview);

                // Update the restaurant rating
                updateRestaurantRating(savedReview.getRestaurant().getId());

                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setReview(savedApiReview);
                reviewResponse.setSuccess(true);

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

    @Transactional
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
        List<ReviewEntity> reviews = reviewRepository.findByRestaurant_Id(restaurantId);

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
