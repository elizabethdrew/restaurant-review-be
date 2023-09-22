package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {
    Optional<CuisineEntity> findByName(String name);

}
