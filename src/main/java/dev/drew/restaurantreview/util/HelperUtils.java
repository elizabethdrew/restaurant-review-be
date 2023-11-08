package dev.drew.restaurantreview.util;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.specification.RestaurantSpecification;
import dev.drew.restaurantreview.repository.specification.ReviewSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelperUtils {

    private final RestaurantRepository restaurantRepository;

    private final ReviewRepository reviewRepository;

    public HelperUtils(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    public RestaurantEntity getRestaurantHelper(Integer restaurantId) throws RestaurantNotFoundException {
        log.info("Looking For Restaurant...");
        RestaurantEntity restaurantEntity = restaurantRepository.findOne(
                Specification.where(RestaurantSpecification.hasId(restaurantId.longValue()))
                        .and(RestaurantSpecification.isNotDeleted())
        ).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));
        log.info("Restaurant Found");
        return restaurantEntity;

    }

    public ReviewEntity getReviewHelper (Integer reviewId) throws ReviewNotFoundException {
        log.info("Looking For Review...");
        ReviewEntity reviewEntity = reviewRepository.findOne(
                Specification.where(ReviewSpecification.hasId(Long.valueOf(reviewId)))
                        .and(ReviewSpecification.isNotDeleted())
        ).orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found"));
        log.info("Review Found");
        return reviewEntity;
    }
}
