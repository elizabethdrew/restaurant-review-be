package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.ClaimEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {

    Optional<ClaimEntity> findByRestaurantAndClaimant(RestaurantEntity restaurant, UserEntity currentUser);

    List<ClaimEntity> findByStatus(ClaimEntity.ClaimStatus claimStatus);
}
