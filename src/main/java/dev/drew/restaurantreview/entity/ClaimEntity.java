package dev.drew.restaurantreview.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "restaurant_claim")
public class ClaimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "claimant_id")
    private UserEntity claimant;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setRestaurantId(Long restaurantId) {
    }

    public enum ClaimStatus {
        PENDING,
        ACCEPTED,
        DENIED
    }
}