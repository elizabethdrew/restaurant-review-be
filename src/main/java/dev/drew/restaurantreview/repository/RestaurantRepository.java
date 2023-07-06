package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// The RestaurantRepository interface is responsible for interacting with the database to manage RestaurantEntity objects.
@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

}