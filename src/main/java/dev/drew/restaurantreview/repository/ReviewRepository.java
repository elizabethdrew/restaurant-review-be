package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByRestaurantId(Long restaurantId);

}
