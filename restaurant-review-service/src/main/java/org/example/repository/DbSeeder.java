package org.example.repository;

import org.example.model.RestaurantModel;
import org.openapitools.client.model.Restaurant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private RestaurantRepository restaurantRepository;

    public DbSeeder(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        Restaurant maccyD = new Restaurant("MaccyD", "Bristol", 3);
        Restaurant burgerQ = new Restaurant("BurgerQ", "London", 2);
        Restaurant asado = new Restaurant("Asado", "Bristol", 5);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(maccyD);
        restaurants.add(burgerQ);
        restaurants.add(asado);

        this.restaurantRepository.save(restaurants);


    }
}
