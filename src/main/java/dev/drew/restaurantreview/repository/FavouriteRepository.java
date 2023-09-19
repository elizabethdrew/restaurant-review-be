package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.FavouriteEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {

    Optional<FavouriteEntity> findByRestaurantAndUser(RestaurantEntity restaurant, UserEntity user);
}
