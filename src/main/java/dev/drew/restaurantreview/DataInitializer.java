//package dev.drew.restaurantreview;
//
//import dev.drew.restaurantreview.entity.Restaurant;
//import dev.drew.restaurantreview.repository.RestaurantRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.OffsetDateTime;
//import java.util.Arrays;
//
//@Configuration
//public class DataInitializer {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Bean
//    public CommandLineRunner initDatabase(RestaurantRepository restaurantRepository) {
//        return args -> {
//            // Define initial data
//            Restaurant restaurant1 = new Restaurant();
//            restaurant1.setName("Drew's Diner");
//            restaurant1.setCity("New York");
//            restaurant1.setRating(4);
//            restaurant1.setCreatedAt(OffsetDateTime.now());
//
//            Restaurant restaurant2 = new Restaurant();
//            restaurant2.setName("Tasty Corner");
//            restaurant2.setCity("Los Angeles");
//            restaurant2.setRating(5);
//            restaurant2.setCreatedAt(OffsetDateTime.now());
//
//            // Save initial data to the database
//            restaurantRepository.saveAll(Arrays.asList(restaurant1, restaurant2));
//
//            logger.info("Initial data inserted.");
//        };
//    }
//}