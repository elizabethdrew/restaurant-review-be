package dev.drew.restaurantreview.auth;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    Optional<JwtBlacklist> findByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM JwtBlacklist t WHERE t.expiration < ?1")
    int deleteExpiredTokens(Date currentTime);
}
