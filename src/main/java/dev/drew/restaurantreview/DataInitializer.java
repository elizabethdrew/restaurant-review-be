//package dev.drew.restaurantreview;
//
//import dev.drew.restaurantreview.entity.UserEntity;
//import dev.drew.restaurantreview.repository.RestaurantRepository;
//import dev.drew.restaurantreview.repository.UserRepository;
//import org.openapitools.model.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.OffsetDateTime;
//import java.util.Arrays;
//
//import static org.openapitools.model.User.RoleEnum.ADMIN;
//
//@Configuration
//public class DataInitializer {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    public DataInitializer(BCryptPasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Bean
//    public CommandLineRunner initDatabase(UserRepository userRepository) {
//        return args -> {
//
//            UserEntity user1 = new UserEntity();
//            user1.setName("Admin");
//            user1.setUsername("anotheradmin");
//            user1.setEmail("anotheradmin@email.com");
//            user1.setRole(ADMIN);
//            user1.setPassword(passwordEncoder.encode("password"));
//            user1.setCreatedAt(OffsetDateTime.now());
//
//
//            // Save initial data to the database
//            userRepository.saveAll(Arrays.asList(user1));
//
//            logger.info("Initial data inserted.");
//        };
//    }
//}