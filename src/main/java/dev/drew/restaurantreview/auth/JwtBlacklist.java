package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "blacklisted_tokens")
@Data
public class JwtBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

}
