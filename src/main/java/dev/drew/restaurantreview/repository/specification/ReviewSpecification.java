package dev.drew.restaurantreview.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import dev.drew.restaurantreview.entity.ReviewEntity;

import java.time.OffsetDateTime;

public class ReviewSpecification {

    public static Specification<ReviewEntity> hasRestaurantId(Long restaurantId) {
        return (root, query, criteriaBuilder) -> restaurantId == null ? null : criteriaBuilder.equal(root.get("restaurant").get("id"), restaurantId);
    }

    public static Specification<ReviewEntity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> userId == null ? null : criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<ReviewEntity> hasRating(Integer rating) {
        return (root, query, criteriaBuilder) -> rating == null ? null : criteriaBuilder.equal(root.get("rating"), rating);
    }

    public static Specification<ReviewEntity> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), false);
    }

    public static Specification<ReviewEntity> isCreatedAfter(OffsetDateTime dateTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dateTime);
    }


    public static Specification<ReviewEntity> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }
}
