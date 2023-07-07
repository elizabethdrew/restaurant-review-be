package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
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
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
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

        // Check if the restaurant has been deleted
        if(currentRestaurant.getIsDeleted()) {
            throw new RestaurantNotFoundException("Restaurant with ID" + reviewInput.getRestaurantId() + " is deleted.");
        }

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the user has already reviewed the restaurant within the last year
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);
        List<ReviewEntity> existingReviews = reviewRepository.findByUser_IdAndRestaurant_IdAndIsDeletedFalse(currentUserId, reviewInput.getRestaurantId());

        boolean hasReviewWithinOneYear = existingReviews.stream()
                .anyMatch(review -> review.getCreatedAt().isAfter(oneYearAgo));

        if (hasReviewWithinOneYear) {
            // Return error response if a review within the last year exists
            throw new IllegalArgumentException("User already reviewed");
        }

        // Add the new review
        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());

        // Set the restaurant and user entities
        review.setRestaurant(currentRestaurant);
        review.setUser(currentUser);

        ReviewEntity savedReview = reviewRepository.save(review);
        Review savedApiReview = reviewMapper.toReview(savedReview);

        // Update the restaurant rating
        updateRestaurantRating(savedReview.getRestaurantId());

        return savedApiReview;
    }

    public List<Review> getAllReviews(Long restaurantId, Long userId, Integer rating) {

        Stream<ReviewEntity> filteredEntities = reviewRepository.findAll().stream()
                .filter(r -> !r.getIsDeleted());

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
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId.longValue())
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        if (reviewEntity.getIsDeleted()) {
            throw new ReviewNotFoundException("Review with ID: " + reviewId + " has been deleted");
        }

        return reviewMapper.toReview(reviewEntity);
    }

    @Transactional
    public Review updateReviewById(Integer reviewId, ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {

        // Retrieve the review with the specified ID from the repository
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId.longValue())
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        // Check if the restaurant has been deleted
        if(reviewEntity.getIsDeleted()) {
            throw new ReviewNotFoundException("Review with ID" + reviewId + " is deleted.");
        }

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrOwner(reviewEntity, reviewUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this review");
        }

            // Manually set the updated properties from the ReviewInput object
            reviewEntity.setRating(reviewInput.getRating());
            reviewEntity.setComment(reviewInput.getComment());
            reviewEntity.setUpdatedAt(OffsetDateTime.now());

            ReviewEntity savedReview = reviewRepository.save(reviewEntity);
            Review savedApiReview = reviewMapper.toReview(savedReview);

            // Update the restaurant rating
            updateRestaurantRating(savedReview.getRestaurant().getId());

            return savedApiReview;
    }

    @Transactional
    public void deleteReviewById(Integer reviewId) {

        // Retrieve the review with the specified ID from the repository
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId.longValue())
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found"));

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrOwner(reviewEntity, reviewUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to delete this review");
        }

        // Save restaurant ID before deleting review
        Long restaurantId = reviewEntity.getRestaurantId();

        // Mark Review as deleted
        reviewEntity.setIsDeleted(true);
        reviewRepository.save(reviewEntity);

        // Update restaurant rating
        updateRestaurantRating(restaurantId);
    }

    public void updateRestaurantRating(Long restaurantId) {
        List<ReviewEntity> reviews = reviewRepository.findAllByRestaurant_IdAndIsDeletedFalse(restaurantId);

        if (!reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .filter(review -> !review.getIsDeleted())
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
