package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.ReviewNotFoundException;
import dev.drew.restaurantreview.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Review;
import org.openapitools.model.ReviewInput;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    @WithMockUser(username = "YOUR_USER", password = "YOUR_PASSWORD")
    void testAddNewReview() throws Exception {
        ReviewInput input = new ReviewInput().restaurantId(1L).rating(5);
        Review review = new Review().id(1L).restaurantId(input.getRestaurantId()).rating(input.getRating());

        when(reviewService.addNewReview(any(ReviewInput.class))).thenReturn(review);

        mockMvc.perform(post("/api/v1/review/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "YOUR_USER", password = "YOUR_PASSWORD")
    void testDeleteReviewById() throws Exception {
        int reviewId = 1;

        mockMvc.perform(delete("/api/v1/review/{reviewId}/delete", reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllReviews() throws Exception {
        List<Review> reviews = Arrays.asList(
                new Review().id(1L).restaurantId(1L).userId(1L).rating(5),
                new Review().id(2L).restaurantId(2L).userId(2L).rating(4)
        );

        when(reviewService.getAllReviews(null, null, null)).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReviewById() throws Exception {
        int reviewId = 1;
        Review review = new Review().id((long)reviewId).restaurantId(1L).userId(1L).rating(5);

        when(reviewService.getReviewById(eq(reviewId))).thenReturn(review);

        mockMvc.perform(get("/api/v1/review/{reviewId}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "YOUR_USER", password

            = "YOUR_PASSWORD")
    void testUpdateReviewById() throws Exception {
        int reviewId = 1;
        ReviewInput input = new ReviewInput().restaurantId(1L).rating(5);
        Review updatedReview = new Review().id((long)reviewId).restaurantId(input.getRestaurantId()).rating(input.getRating());

        when(reviewService.updateReviewById(eq(reviewId), any(ReviewInput.class))).thenReturn(updatedReview);

        mockMvc.perform(put("/api/v1/review/{reviewId}/edit", reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isOk());
    }
}
