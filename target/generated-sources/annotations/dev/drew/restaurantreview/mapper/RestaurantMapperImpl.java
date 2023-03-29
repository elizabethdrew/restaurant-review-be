package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import javax.annotation.processing.Generated;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-29T16:25:04+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant toRestaurant(RestaurantEntity restaurantEntity) {
        if ( restaurantEntity == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setId( restaurantEntity.getId() );
        restaurant.setName( restaurantEntity.getName() );
        restaurant.setCity( restaurantEntity.getCity() );
        restaurant.setRating( restaurantEntity.getRating() );
        restaurant.setCreatedAt( restaurantEntity.getCreatedAt() );

        return restaurant;
    }

    @Override
    public RestaurantEntity toRestaurantEntity(RestaurantInput restaurantInput) {
        if ( restaurantInput == null ) {
            return null;
        }

        RestaurantEntity restaurantEntity = new RestaurantEntity();

        restaurantEntity.setName( restaurantInput.getName() );
        restaurantEntity.setCity( restaurantInput.getCity() );
        restaurantEntity.setRating( restaurantInput.getRating() );

        return restaurantEntity;
    }

    @Override
    public RestaurantInput toRestaurantInput(RestaurantEntity restaurantEntity) {
        if ( restaurantEntity == null ) {
            return null;
        }

        RestaurantInput restaurantInput = new RestaurantInput();

        restaurantInput.setName( restaurantEntity.getName() );
        restaurantInput.setCity( restaurantEntity.getCity() );
        restaurantInput.setRating( restaurantEntity.getRating() );

        return restaurantInput;
    }
}
