package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The UserRepository interface is responsible for interacting with the database to manage UserEntity objects.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByIdAndIsDeletedFalse(Long id);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}

/*
This code uses derived queries: https://www.baeldung.com/spring-data-derived-queries
 */
