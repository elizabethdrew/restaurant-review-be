package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


// The ReviewRepository interface is responsible for interacting with the database to manage ReviewEntity objects.
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, JpaSpecificationExecutor<ReviewEntity> {
}

/*
This code uses JPA Criteria Queries https://www.baeldung.com/hibernate-criteria-queries
 */