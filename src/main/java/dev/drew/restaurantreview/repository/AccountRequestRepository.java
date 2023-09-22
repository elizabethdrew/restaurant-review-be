package dev.drew.restaurantreview.repository;

import dev.drew.restaurantreview.entity.AccountRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRequestRepository extends JpaRepository<AccountRequestEntity, Long> {

    List<AccountRequestEntity> findByStatus(AccountRequestEntity.Status status);
}
