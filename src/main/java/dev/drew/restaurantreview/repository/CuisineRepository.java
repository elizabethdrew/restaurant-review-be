package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.CuisineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {
    Optional<CuisineEntity> findByName(String name);

}
