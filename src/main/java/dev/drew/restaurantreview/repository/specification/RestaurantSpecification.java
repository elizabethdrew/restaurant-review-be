package dev.drew.restaurantreview.repository.specification;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RestaurantSpecification {

    // Static method returns a specification used to filter by city
    // This returns a lambda function (Specification) that checks if city is null.
    // If city is null, it returns null which means the filter won't be applied.
    // If city is not null, it checks if the "city" attribute of the RestaurantEntity is equal to the given city.
    public static Specification<RestaurantEntity> hasCity(List<String> cities) {
        return (root, query, criteriaBuilder) -> cities == null || cities.isEmpty() ? null : root.get("city").in(cities);
    }

    public static Specification<RestaurantEntity> hasRating(List<Integer> ratings) {
        return (root, query, criteriaBuilder) -> ratings == null || ratings.isEmpty() ? null : root.get("rating").in(ratings);
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

    public static Specification<RestaurantEntity> hasPriceRange(List<Integer> priceRanges) {
        return (root, query, criteriaBuilder) -> priceRanges == null || priceRanges.isEmpty() ? null : root.get("priceRange").in(priceRanges);
    }

    public static Specification<RestaurantEntity> hasCuisine(List<String> cuisines) {
        return (root, query, criteriaBuilder) -> {
            if (cuisines == null || cuisines.isEmpty()) {
                return null;
            }

            // Create a join on the cuisines attribute of RestaurantEntity
            Join<RestaurantEntity, CuisineEntity> cuisineJoin = root.join("cuisines");

            // Use the 'name' attribute of CuisineEntity for the filtering
            return cuisineJoin.get("name").in(cuisines);
        };
    }


}

