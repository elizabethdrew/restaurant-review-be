package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The UserRepository interface is responsible for interacting with the database to manage UserEntity objects.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Find a user by their username
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByIdAndIsDeletedFalse(Long id);

}

/*
This code uses derived queries: https://www.baeldung.com/spring-data-derived-queries
 */
