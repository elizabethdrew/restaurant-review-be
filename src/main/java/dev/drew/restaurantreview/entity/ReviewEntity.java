package dev.drew.restaurantreview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends org.openapitools.model.Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "rating")
    private Float rating;

    @NotNull
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updatedAt;

}
