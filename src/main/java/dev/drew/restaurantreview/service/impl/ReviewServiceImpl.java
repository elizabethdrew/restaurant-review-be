package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.repository.specification.ReviewSpecification;
import dev.drew.restaurantreview.service.ReviewService;
import dev.drew.restaurantreview.util.HelperUtils;
import dev.drew.restaurantreview.util.SecurityUtils;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PaginatedReviewResponse;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.openapitools.model.UpdateReviewReplyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    private final RestaurantRepository restaurantRepository;

    private final EntityUserIdProvider<ReviewEntity> reviewUserIdProvider = ReviewEntity::getUserId;
    private UserRepository userRepository;

    private final HelperUtils helperUtils;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, RestaurantRepository restaurantRepository, UserRepository userRepository, HelperUtils helperUtils) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.helperUtils = helperUtils;
    }

    // Add a new review to the database
    @Transactional
    public Review addNewReview(ReviewInput reviewInput) {

        log.info("Starting: Add New Review");

        log.info("Getting Restaurant");
        Integer restaurantId = Math.toIntExact(reviewInput.getRestaurantId());
        RestaurantEntity restaurantEntity = helperUtils.getRestaurantHelper(restaurantId);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        Long currentUserId = currentUser.getId();

        // Check if the user is trying to review their own restaurant
        log.info("Checking If User Owns Restaurant");
        UserEntity restaurantOwner = restaurantEntity.getOwner();
        if (restaurantOwner != null && restaurantOwner.getId().equals(currentUserId)) {
            log.info("User Owns Restaurant");
            throw new UserOwnsRestaurantException("Owners cannot review their own restaurants.");
        }
        log.info("User Doesn't Own Restaurant");

        // Check if the user has already reviewed the restaurant within the last year
        log.info("Has User Reviewed This Year Already?");
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);

        Specification<ReviewEntity> spec = Specification
                .where(ReviewSpecification.hasUserId(currentUserId))
                .and(ReviewSpecification.hasRestaurantId(reviewInput.getRestaurantId()))
                .and(ReviewSpecification.isNotDeleted())
                .and(ReviewSpecification.isCreatedAfter(oneYearAgo));

        List<ReviewEntity> existingReviews = reviewRepository.findAll(spec);

        if (!existingReviews.isEmpty()) {
            // Return error response if a review within the last year exists
            log.info("User Has Already Reviewed The Restaurant This Year ");
            throw new DuplicateReviewException("User already reviewed");
        }

        log.info("User Hasn't Reviewed Restaurant Already");

        // Add the new review
        log.info("Creating The Review");
        ReviewEntity review = reviewMapper.toReviewEntity(reviewInput);
        review.setCreatedAt(OffsetDateTime.now());

        // Set the restaurant and user entities
        review.setRestaurant(restaurantEntity);
        review.setUser(currentUser);

        ReviewEntity savedReview = reviewRepository.save(review);
        log.info("Review Saved");

        // Update the restaurant rating
        updateRestaurantRating(savedReview.getRestaurant().getId());

        return reviewMapper.toReview(savedReview);
    }


    private Page<ReviewEntity> findReviews(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable) {
        log.info("Searching for reviews");
        return reviewRepository.findAll(
                Specification.where(ReviewSpecification.hasRestaurantId(restaurantId))
                        .and(ReviewSpecification.hasUserId(userId))
                        .and(ReviewSpecification.hasRating(rating))
                        .and(ReviewSpecification.isNotDeleted()),
                pageable
        );
    }

    public List<Review> getAllReviewsV1(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable) {

        log.info("Starting: Get All Reviews");
        Page<ReviewEntity> filteredEntities = findReviews(restaurantId, userId, rating, pageable);

        log.info("Reviews Incoming!");
        return filteredEntities.stream()
                .map(reviewMapper::toReview)
                .collect(Collectors.toList());
    }

    public PaginatedReviewResponse getAllReviewsV2(Long restaurantId, Long userId, List<Integer> rating, Pageable pageable) {
        log.info("Starting: Get All Reviews");

        Page<ReviewEntity> filteredEntities = findReviews(restaurantId, userId, rating, pageable);

        log.info("Reviews Incoming!");
        List<Review> reviewList = filteredEntities.getContent() // Use getContent() to retrieve the list of items
                .stream()
                .map(reviewMapper::toReview)
                .toList();

        log.info("Creating response object");
        PaginatedReviewResponse response = new PaginatedReviewResponse();
        response.setTotal(filteredEntities.getTotalElements());
        response.setItems(new ArrayList<>(reviewList));

        return response;
    }

    public Review getReviewById(Integer reviewId) throws ReviewNotFoundException {

        log.info("Starting: Get Review By ID");

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        return reviewMapper.toReview(reviewEntity);
    }

    @Transactional
    public Review updateReviewById(Integer reviewId, ReviewInput reviewInput)
            throws ReviewNotFoundException, InsufficientPermissionException {

        log.info("Starting: Update Review");

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!SecurityUtils.isAdminOrCreator(reviewEntity, reviewUserIdProvider)) {
            log.info("Only Admins Or The Original Reviewer Can Update Review");
            throw new InsufficientPermissionException("User does not have permission to update this review");
        }

            // Manually set the updated properties from the ReviewInput object
            log.info("Updating Review");
            reviewEntity.setRating(reviewInput.getRating());
            reviewEntity.setComment(reviewInput.getComment());
            reviewEntity.setUpdatedAt(OffsetDateTime.now());

            ReviewEntity savedReview = reviewRepository.save(reviewEntity);
            log.info("Review Saved");
            Review savedApiReview = reviewMapper.toReview(savedReview);

            // Update the restaurant rating
            updateRestaurantRating(savedReview.getRestaurant().getId());

            return savedApiReview;
    }

    @Transactional
    public void deleteReviewById(Integer reviewId) {

        log.info("Starting: Delete Review");

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        // Check if the current user is an admin or the owner of the restaurant
        if (!SecurityUtils.isAdminOrCreator(reviewEntity, reviewUserIdProvider)) {
            log.info("Only Admins Or The Original Reviewer Can Delete Review");
            throw new InsufficientPermissionException("User does not have permission to delete this review");
        }

        // Save restaurant ID before deleting review
        Long restaurantId = reviewEntity.getRestaurant().getId();

        // Mark Review as deleted
        log.info("Marking Review Deleted");
        reviewEntity.setIsDeleted(true);
        reviewRepository.save(reviewEntity);
        log.info("Review Updated");

        // Update restaurant rating
        updateRestaurantRating(restaurantId);
    }


    public void updateRestaurantRating(Long restaurantId) {

        log.info("Starting: Update Restaurant Average Rating");

        log.info("Finding Reviews For Restaurant");
        Specification<ReviewEntity> spec = Specification
                .where(ReviewSpecification.hasRestaurantId(restaurantId))
                .and(ReviewSpecification.isNotDeleted());

        List<ReviewEntity> reviews = reviewRepository.findAll(spec);

        if (!reviews.isEmpty()) {
            log.info("Calculating Rating Average");
            double averageRating = reviews.stream()
                    .mapToDouble(ReviewEntity::getRating)
                    .average()
                    .orElse(0);

            // Round the average rating to the nearest integer
            int roundedAverageRating = (int) Math.round(averageRating);

            log.info("Finding Restaurant");
            RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).orElse(null);
            if (restaurantEntity != null) {
                log.info("Updating Restaurant Rating");
                restaurantEntity.setRating(roundedAverageRating);
                restaurantRepository.save(restaurantEntity);
                log.info("Restaurant Rating Updated");
            }
        }
    }

    @Override
    public Review addReviewReply(Integer reviewId, UpdateReviewReplyRequest updateReviewReplyRequest) {

        log.info("Starting: Add Review Reply");

        return reviewReplyHelper(reviewId, updateReviewReplyRequest.getReply());
    }

    @Override
    public Review updateReviewReply(Integer reviewId, UpdateReviewReplyRequest updateReviewReplyRequest) {
        log.info("Starting: Update Review Reply");
        return reviewReplyHelper(reviewId, updateReviewReplyRequest.getReply());
    }

    @Override
    public Review deleteReviewReply(Integer reviewId) {
        log.info("Starting: Delete Review Reply");
        return reviewReplyHelper(reviewId, null);
    }

    public Review reviewReplyHelper(Integer reviewId, String reply) {

        ReviewEntity reviewEntity = helperUtils.getReviewHelper(reviewId);

        // Fetch the current user entity
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        Long currentUserId = currentUser.getId();

        // Check if the user is trying to review their own restaurant
        log.info("Checking User Owns Restaurant");
        Optional<UserEntity> restaurantOwner = Optional.ofNullable(reviewEntity.getRestaurant().getOwner());
        if (!restaurantOwner.isPresent() || !restaurantOwner.get().getId().equals(currentUserId)) {
            log.info("User Does Not Own Restaurant");
            throw new NotRestaurantOwnerException("Only restaurant owners can reply to reviews");
        }
        log.info("User Owns Restaurant");

        // Manually set the updated properties from the ReviewInput object
        log.info("Updating Review Reply");
        reviewEntity.setReply(reply);
        reviewEntity.setReplyDate(OffsetDateTime.now());
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        log.info("Review Reply Saved");
        return reviewMapper.toReview(savedReview);

    }
}
