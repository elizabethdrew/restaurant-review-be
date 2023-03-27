package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
