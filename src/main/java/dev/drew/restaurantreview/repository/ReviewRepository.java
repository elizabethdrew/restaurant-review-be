package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// The ReviewRepository interface is responsible for interacting with the database to manage ReviewEntity objects.
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // Method to find all reviews by restaurantId
    List<ReviewEntity> findByRestaurant_Id(Long restaurantId);

    // Method to find all reviews by userId and restaurantId
    List<ReviewEntity> findByUser_IdAndRestaurant_Id(Long userId, Long restaurantId);

    List<ReviewEntity> findByRating(Integer rating);

    List<ReviewEntity> findAllByRestaurant_IdAndIsDeletedFalse(Long restaurantId);
}
