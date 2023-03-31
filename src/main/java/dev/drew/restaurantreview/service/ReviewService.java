package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.springframework.stereotype.Service;

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
}
