package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The RestaurantRepository interface is responsible for interacting with the database to manage RestaurantEntity objects.
@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long>, JpaSpecificationExecutor<RestaurantEntity> {

    Optional<RestaurantEntity> findByNameAndCity(String name, String city);

}

/*
This code uses JPA Criteria Queries https://www.baeldung.com/hibernate-criteria-queries
 */