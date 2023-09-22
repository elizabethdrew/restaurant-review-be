package dev.drew.restaurantreview.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "admin_account_requests")
public class AccountRequestEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity user;

        @Enumerated(EnumType.STRING)
        private Status status;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;


    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
