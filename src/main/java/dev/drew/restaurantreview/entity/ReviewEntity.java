package dev.drew.restaurantreview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class ReviewEntity extends org.openapitools.model.Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Column(name = "rating")
    private Integer rating;

    @NotNull
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updatedAt;

    @Getter
    @Transient
    private String restaurant_name;

    @Getter
    @Transient
    private String restaurant_city;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public Integer getRating() {
        return rating;
    }

    @Override
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public void setRestaurant_city(String restaurant_city) {
        this.restaurant_city = restaurant_city;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRestaurant(), that.getRestaurant()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getRating(), that.getRating()) && Objects.equals(getComment(), that.getComment()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && Objects.equals(getRestaurant_name(), that.getRestaurant_name()) && Objects.equals(getRestaurant_city(), that.getRestaurant_city()) && Objects.equals(getIsDeleted(), that.getIsDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getRestaurant(), getUser(), getRating(), getComment(), getCreatedAt(), getUpdatedAt(), getRestaurant_name(), getRestaurant_city(), getIsDeleted());
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                ", user=" + user +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_city='" + restaurant_city + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}