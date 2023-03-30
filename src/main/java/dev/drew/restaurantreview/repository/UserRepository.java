package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.UserEntity;
import org.openapitools.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

}
