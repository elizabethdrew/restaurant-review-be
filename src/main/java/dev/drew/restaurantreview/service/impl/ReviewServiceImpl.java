package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.repository.specification.ReviewSpecification;
import dev.drew.restaurantreview.service.ReviewService;
import dev.drew.restaurantreview.util.HelperUtils;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static dev.drew.restaurantreview.util.SecurityUtils.*;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    private final RestaurantMapper restaurantMapper;

    private final RestaurantRepository restaurantRepository;

    private final EntityUserIdProvider<ReviewEntity> reviewUserIdProvider = ReviewEntity::getUserId;
    private UserRepository userRepository;

    private HelperUtils helperUtils;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    // Add a new review to the database
    @Transactional
    public Review addNewReview(ReviewInput reviewInput) {

        Integer restaurantId = Math.toIntExact(reviewInput.getRestaurantId());
        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Fetch the current user entity
        Long currentUserId = getCurrentUserId();
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the user is trying to review their own restaurant
        UserEntity restaurantOwner = restaurantEntity.getOwner();
        if (restaurantOwner != null && restaurantOwner.getId().equals(currentUserId)) {
            throw new UserOwnsRestaurantException("Owners cannot review their own restaurants.");
        }

        // Check if the user has already reviewed the restaurant within the last year
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);

        Specification<ReviewEntity> spec = Specification
                .where(ReviewSpecification.hasUserId(currentUserId))
                .and(ReviewSpecification.hasRestaurantId(reviewInput.getRestaurantId()))
                .and(ReviewSpecification.isNotDeleted())
                .and(ReviewSpecification.isCreatedAfter(oneYearAgo));

        List<ReviewEntity> existingReviews = reviewRepository.findAll(spec);

        if (!existingReviews.isEmpty()) {
            // Return error response if a review within the last year exists
            throw new DuplicateReviewException("User already reviewed");
        }

        // Add the new review
        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());

        // Set the restaurant and user entities
        review.setRestaurant(restaurantEntity);
        review.setUser(currentUser);

        ReviewEntity savedReview = reviewRepository.save(review);

        // Update the restaurant rating
        updateRestaurantRating(savedReview.getRestaurant().getId());

        return reviewMapper.toReview(savedReview);
    }

    @Override
    public List<Review> getAllReviews(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable) {
        Page<ReviewEntity> filteredEntities = reviewRepository.findAll(
            Specification.where(ReviewSpecification.hasRestaurantId(restaurantId))
                .and(ReviewSpecification.hasUserId(userId))
                .and(ReviewSpecification.hasRating(rating))
                .and(ReviewSpecification.isNotDeleted()),
                pageable
        );

        return filteredEntities.stream().map(reviewMapper::toReview).collect(Collectors.toList());
    }


    public Review getReviewById(Integer reviewId) throws ReviewNotFoundException {

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        return reviewMapper.toReview(reviewEntity);
    }

    @Transactional
    public Review updateReviewById(Integer reviewId, ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrCreator(reviewEntity, reviewUserIdProvider)) {
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

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!isAdminOrCreator(reviewEntity, reviewUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to delete this review");
        }

        // Save restaurant ID before deleting review
        Long restaurantId = reviewEntity.getRestaurant().getId();

        // Mark Review as deleted
        reviewEntity.setIsDeleted(true);
        reviewRepository.save(reviewEntity);

        // Update restaurant rating
        updateRestaurantRating(restaurantId);
    }

    public void updateRestaurantRating(Long restaurantId) {

        Specification<ReviewEntity> spec = Specification
                .where(ReviewSpecification.hasRestaurantId(restaurantId))
                .and(ReviewSpecification.isNotDeleted());

        List<ReviewEntity> reviews = reviewRepository.findAll(spec);

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
