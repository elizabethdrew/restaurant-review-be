package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.ReviewMapper;
import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.impl.ReviewServiceImpl;
import dev.drew.restaurantreview.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private SecurityUser securityUser;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        securityUser = createSecurityUserWithRole(User.RoleEnum.ADMIN);
        SecurityUtils.setCurrentUser(securityUser);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private SecurityUser createSecurityUserWithRole(User.RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("password");
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    @Test
    void testAddNewReview() {

        // Prepare input data and expected response
        ReviewInput input = new ReviewInput().restaurantId(1L).rating(5).comment("Great Food");
        ReviewEntity review = new ReviewEntity();
        review.setRestaurantId(input.getRestaurantId());
        review.setRating(input.getRating());
        review.setComment(input.getComment());

        Review reviewResponse = new Review().restaurantId(input.getRestaurantId()).rating(input.getRating()).comment(input.getComment());

        // Mock the repository and mapper calls
        when(restaurantRepository.findById(input.getRestaurantId())).thenReturn(Optional.of(new RestaurantEntity()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(securityUser.getUserEntity()));
        when(reviewMapper.toReviewEntity(any(ReviewInput.class))).thenReturn(review);
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(review);
        when(reviewMapper.toReview(any(ReviewEntity.class))).thenReturn(reviewResponse);

        // Call the service method
        Review response = reviewServiceImpl.addNewReview(input);

        // Verify the response status
        assertEquals(input.getRestaurantId(), response.getRestaurantId());
        assertEquals(input.getRating(), response.getRating());
        assertEquals(input.getComment(), response.getComment());
    }
}
