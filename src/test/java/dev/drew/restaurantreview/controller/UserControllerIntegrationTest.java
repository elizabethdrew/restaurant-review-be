package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Restaurant;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static junit.framework.Assert.assertEquals;
import static org.openapitools.model.UserInput.RoleEnum.ADMIN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",password="password",roles={"ADMIN"})
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long createdUserId;

    @BeforeEach
    public void setup() throws Exception {
        UserInput userInput = new UserInput()
                .name("Test User")
                .email("test2@test.com")
                .password("password")
                .username("testuser2")
                .role(ADMIN);

        String userInputJson = objectMapper.writeValueAsString(userInput);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userInputJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        if (result.getResponse().getStatus() != HttpStatus.CREATED.value()) {
            throw new RuntimeException("Failed to add test user.");
        }

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode userNode = jsonNode.get("user");
        User user = objectMapper.treeToValue(userNode, User.class);

        createdUserId = user.getId();
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (createdUserId != null) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}", createdUserId))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    public void testGetUserById_exists() throws Exception {
        Integer userId = createdUserId.intValue();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        User user = objectMapper.readValue(jsonResponse, User.class);
        assertEquals((long)userId, (long)user.getId());
    }

    @Test
    public void testGetUserById_notFound() throws Exception {
        Integer userId = -1; // Use an ID that doesn't exist in the database

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userId))
                .andExpect(status().isNotFound());
    }

}