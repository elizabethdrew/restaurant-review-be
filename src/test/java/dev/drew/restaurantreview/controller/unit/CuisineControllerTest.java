package dev.drew.restaurantreview.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.controller.CuisineController;
import dev.drew.restaurantreview.dto.CuisineDto;
import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.service.CuisineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CuisineControllerTest {

    @InjectMocks
    private CuisineController cuisineController;

    @Mock
    private CuisineService cuisineService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cuisineController).build();
    }

    @Test
    void testGetAllCuisines() throws Exception {
        List<String> cuisines = Arrays.asList("Italian", "Chinese");

        when(cuisineService.getAllCuisineNames()).thenReturn(cuisines);

        mockMvc.perform(get("/api/v1/cuisines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "YOUR_USER", password = "YOUR_PASSWORD")
    void testAddNewCuisine() throws Exception {
        CuisineDto dto = new CuisineDto();
        dto.setName("Italian");

        CuisineEntity entity = new CuisineEntity();
        entity.setName("Italian");

        when(cuisineService.addCuisine(dto.getName())).thenReturn(entity);

        mockMvc.perform(post("/api/v1/cuisines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "YOUR_USER", password = "YOUR_PASSWORD")
    void testDeleteCuisine() throws Exception {
        String cuisineName = "Italian";

        mockMvc.perform(delete("/api/v1/cuisines/{name}", cuisineName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}