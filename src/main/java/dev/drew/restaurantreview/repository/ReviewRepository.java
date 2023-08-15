package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// The ReviewRepository interface is responsible for interacting with the database to manage ReviewEntity objects.
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("SELECT r FROM ReviewEntity r WHERE (:restaurantId is null or r.restaurant.id = :restaurantId) and (:rating is null or r.rating = :rating) and (:userId is null or r.user.id = :userId) and r.isDeleted = false")
    Page<ReviewEntity> findAllByRestaurantIdAndUserIdAndRating(
            @Param("restaurantId") Long restaurantId,
            @Param("userId") Long userId,
            @Param("rating") Integer rating,
            Pageable pageable);

    @Query("SELECT r FROM ReviewEntity r WHERE r.user.id = :userId and r.restaurant.id = :restaurantId and r.isDeleted = false")
    List<ReviewEntity> findValidReviewsByUserIdAndRestaurantId(
            @Param("userId") Long userId,
            @Param("restaurantId") Long restaurantId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.restaurant.id = :restaurantId and r.isDeleted = false")
    List<ReviewEntity> findValidReviewsByRestaurantId(
            @Param("restaurantId") Long restaurantId);

}

/*
This code uses @Query: https://www.baeldung.com/spring-data-jpa-null-parameters#Ignoring
 */
