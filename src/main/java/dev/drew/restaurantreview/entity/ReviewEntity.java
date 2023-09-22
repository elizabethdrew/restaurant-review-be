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

    //@Getter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    //@Getter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "reply")
    private String reply;

    @Override
    public String getReply() {
        return reply;
    }

    @Override
    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public OffsetDateTime getReplyDate() {
        return replyDate;
    }

    @Override
    public void setReplyDate(OffsetDateTime replyDate) {
        this.replyDate = replyDate;
    }

    @Column(name = "reply_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime replyDate;

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
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRestaurant(), that.getRestaurant()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getRating(), that.getRating()) && Objects.equals(getComment(), that.getComment()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && Objects.equals(getIsDeleted(), that.getIsDeleted()) && Objects.equals(getReply(), that.getReply()) && Objects.equals(getReplyDate(), that.getReplyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getRestaurant(), getUser(), getRating(), getComment(), getCreatedAt(), getUpdatedAt(), getIsDeleted(), getReply(), getReplyDate());
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
                ", isDeleted=" + isDeleted +
                ", reply='" + reply + '\'' +
                ", replyDate=" + replyDate +
                '}';
    }
}