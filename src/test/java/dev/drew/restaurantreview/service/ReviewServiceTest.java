package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    @Mock
    private ReviewService reviewService;

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

//    @Test
//    public void testGetAllReviews() {
//        // Prepare expected data
//        List<ReviewEntity> reviewEntities = new ArrayList<>();
//
//        ReviewEntity reviewEntity1 = new ReviewEntity();
//        reviewEntity1.setId(1L);
//        reviewEntity1.setRating(5);
//        reviewEntities.add(reviewEntity1);
//
//        ReviewEntity reviewEntity2 = new ReviewEntity();
//        reviewEntity2.setId(2L);
//        reviewEntity2.setRating(4);
//        reviewEntities.add(reviewEntity2);
//
//        // Mock the repository call
//        when(reviewRepository.findAll()).thenReturn(reviewEntities);
//
//        // Call the service method
//        List<Review> response = reviewServiceImpl.getAllReviews(null, null, null);
//
//        // Verify the response status and data
//        assertEquals(2, response.size());
//    }

    @Test
    public void testGetReviewById() {
        // Prepare expected data
        Long reviewId = 1L;
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewId);
        reviewEntity.setRating(5);

        Review expectedReview = new Review();
        expectedReview.setId(reviewId);
        expectedReview.setRating(5);

        // Mock the repository call
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));

        // Mock the mapper call
        when(reviewMapper.toReview(reviewEntity)).thenReturn(expectedReview);

        // Call the service method
        Review response = reviewServiceImpl.getReviewById(reviewId.intValue());

        // Verify the response status
        assertNotNull(response);
        assertEquals(expectedReview.getId(), response.getId());
        assertEquals(expectedReview.getComment(), response.getComment());
        assertEquals(expectedReview.getRating(), response.getRating());
    }

    @Test
    void testUpdateReviewById() {
        // Prepare input data
        Long reviewId = 1L;
        Long restaurantId = 2L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId); // Set the ID for the restaurant entity

        ReviewInput updatedInput = new ReviewInput().rating(3).comment("Updated Comment");

        // Prepare expected data
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewId);
        reviewEntity.setRating(5);
        reviewEntity.setUser(securityUser.getUserEntity());
        reviewEntity.setRestaurant(restaurantEntity); // Associate the restaurant entity

        ReviewEntity updatedEntity = new ReviewEntity();
        updatedEntity.setId(reviewId);
        updatedEntity.setRating(updatedInput.getRating());
        updatedEntity.setComment(updatedInput.getComment());
        updatedEntity.setUser(securityUser.getUserEntity());
        updatedEntity.setRestaurant(restaurantEntity); // Associate the restaurant entity

        // Mock the repository and mapper calls
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(updatedEntity);

        Review updatedReviewResponse = new Review().id(reviewId).rating(updatedInput.getRating()).comment(updatedInput.getComment());
        when(reviewMapper.toReview(any(ReviewEntity.class))).thenReturn(updatedReviewResponse);

        // Call the service method
        Review updatedReview = reviewServiceImpl.updateReviewById(reviewId.intValue(), updatedInput);

        // Verify the response
        assertEquals(updatedInput.getRating(), updatedReview.getRating());
        assertEquals(updatedInput.getComment(), updatedReview.getComment());
    }

    @Test
    void testDeleteReviewByIdNotFound() {
        // Prepare expected data
        Long reviewId = 1L;

        // Mock the repository call
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Verify the response status
        assertThrows(ReviewNotFoundException.class, () -> reviewServiceImpl.deleteReviewById(reviewId.intValue()));

        verify(reviewRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteReviewByIdForbidden() {
        // Prepare expected data
        Long reviewId = 1L;
        Long restaurantId = 2L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId); // Set the ID for the restaurant entity
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewId);
        reviewEntity.setRating(5);
        reviewEntity.setRestaurant(restaurantEntity); // Associate the restaurant entity
        reviewEntity.setUserId(2L); // Different user ID

        // Mock the repository call
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));

        // Change the current user to a non-admin role
        SecurityUser securityUser = createSecurityUserWithRole(User.RoleEnum.REVIEWER);
        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                securityUser, null, securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));

        assertThrows(InsufficientPermissionException.class, () -> reviewServiceImpl.deleteReviewById(reviewId.intValue()));

        verify(reviewRepository, never()).deleteById(anyLong());
    }
}
