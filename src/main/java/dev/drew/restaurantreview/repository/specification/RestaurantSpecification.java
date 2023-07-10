package dev.drew.restaurantreview.repository.specification;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecification {

    // Static method returns a specification used to filter by city
    // This returns a lambda function (Specification) that checks if city is null.
    // If city is null, it returns null which means the filter won't be applied.
    // If city is not null, it checks if the "city" attribute of the RestaurantEntity is equal to the given city.
    public static Specification<RestaurantEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) -> city == null ? null : criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<RestaurantEntity> hasRating(Integer rating) {
        return (root, query, criteriaBuilder) -> rating == null ? null : criteriaBuilder.equal(root.get("rating"), rating);
    }

    public static Specification<RestaurantEntity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> userId == null ? null : criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<RestaurantEntity> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }

    public static Specification<RestaurantEntity> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }
}

