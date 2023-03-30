package dev.drew.restaurantreview.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static junit.framework.Assert.assertEquals;
import static org.openapitools.model.UserInput.RoleEnum.ADMIN;
import static org.openapitools.model.UserInput.RoleEnum.REVIEWER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
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
                .email("test3@test.com")
                .password("password")
                .username("testuser3")
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

        // Assertions to check if the user was created successfully
        Assertions.assertNotNull(createdUserId, "The created user ID should not be null");
        Assertions.assertTrue(createdUserId > 0, "The created user ID should be greater than 0");
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (createdUserId != null) {
            // Fetch the user before deleting it
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", createdUserId))
                    .andExpect(status().isOk())
                    .andReturn();

            // Delete the user
            mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}", createdUserId))
                    .andExpect(status().isNoContent());

            // Assertions to check if the user was deleted successfully
            result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", createdUserId))
                    .andExpect(status().isNotFound())
                    .andReturn();
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

    @Test
    public void testUpdateUserById() throws Exception {
        Integer userId = createdUserId.intValue();

        // Create a new UserInput object with the updated information
        UserInput updatedUserInput = new UserInput()
                .name("Updated User")
                .email("updated@test.com")
                .password("newpassword")
                .username("updateduser")
                .role(REVIEWER);

        // Convert the updated UserInput object to a JSON string
        String updatedUserJson = objectMapper.writeValueAsString(updatedUserInput);

        // Update the user using a PUT request and MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the JSON response
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode userNode = jsonNode.get("user");
        User updatedUser = objectMapper.treeToValue(userNode, User.class);

        // Check if the user was updated successfully
        Assertions.assertEquals(userId.intValue(), updatedUser.getId().intValue());
        Assertions.assertEquals(updatedUserInput.getName(), updatedUser.getName());
        Assertions.assertEquals(updatedUserInput.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(updatedUserInput.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(updatedUserInput.getUsername(), updatedUser.getUsername());
        Assertions.assertEquals(updatedUserInput.getRole(), UserInput.RoleEnum.valueOf(updatedUser.getRole().toString()));
    }

}