/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.Error;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T12:13:28.916070+01:00[Europe/London]")
@Validated
@Tag(name = "restaurants", description = "the restaurants API")
public interface RestaurantsApi {

    /**
     * POST /restaurants : Add a new restaurant
     * Adds a new restaurant to the database.
     *
     * @param restaurantInput The restaurant to add. (required)
     * @return The new restaurant (status code 201)
     *         or Invalid input (status code 400)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "addNewRestaurant",
        summary = "Add a new restaurant",
        description = "Adds a new restaurant to the database.",
        tags = { "Restaurants" },
        responses = {
            @ApiResponse(responseCode = "201", description = "The new restaurant", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/restaurants",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<RestaurantResponse> addNewRestaurant(
        @Parameter(name = "RestaurantInput", description = "The restaurant to add.", required = true) @Valid @RequestBody RestaurantInput restaurantInput
    );


    /**
     * DELETE /restaurants/{restaurantId} : Delete a restaurant
     * Deletes a restaurant by ID.
     *
     * @param restaurantId The ID of the restaurant to delete. (required)
     * @return Restaurant deleted successfully. (status code 204)
     *         or Restaurant not found. (status code 404)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "deleteRestaurantById",
        summary = "Delete a restaurant",
        description = "Deletes a restaurant by ID.",
        tags = { "Restaurants" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/restaurants/{restaurantId}",
        produces = { "application/json" }
    )
    ResponseEntity<Void> deleteRestaurantById(
        @Min(1) @Parameter(name = "restaurantId", description = "The ID of the restaurant to delete.", required = true, in = ParameterIn.PATH) @PathVariable("restaurantId") Integer restaurantId
    );


    /**
     * GET /restaurants : Get all restaurants
     * Returns a list of all restaurants.
     *
     * @param city The city to filter restaurants by. (optional)
     * @param rating The rating value to filter restaurants by. (optional)
     * @param userId The user ID to filter restaurants by. (optional)
     * @return A list of restaurants. (status code 200)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "getAllRestaurants",
        summary = "Get all restaurants",
        description = "Returns a list of all restaurants.",
        tags = { "Restaurants" },
        responses = {
            @ApiResponse(responseCode = "200", description = "A list of restaurants.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Restaurant.class)))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/restaurants",
        produces = { "application/json" }
    )
    ResponseEntity<List<Restaurant>> getAllRestaurants(
        @Parameter(name = "city", description = "The city to filter restaurants by.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "city", required = false) String city,
        @Min(1) @Max(5) @Parameter(name = "rating", description = "The rating value to filter restaurants by.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "rating", required = false) Integer rating,
        @Parameter(name = "user_id", description = "The user ID to filter restaurants by.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "user_id", required = false) Long userId
    );


    /**
     * GET /restaurants/{restaurantId} : Get a restaurant by ID
     * Returns a single restaurant by ID.
     *
     * @param restaurantId The ID of the restaurant to retrieve. (required)
     * @return The restaurant. (status code 200)
     */
    @Operation(
        operationId = "getRestaurantById",
        summary = "Get a restaurant by ID",
        description = "Returns a single restaurant by ID.",
        tags = { "Restaurants" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The restaurant.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/restaurants/{restaurantId}",
        produces = { "application/json" }
    )
    ResponseEntity<Restaurant> getRestaurantById(
        @Min(1) @Parameter(name = "restaurantId", description = "The ID of the restaurant to retrieve.", required = true, in = ParameterIn.PATH) @PathVariable("restaurantId") Integer restaurantId
    );


    /**
     * PUT /restaurants/{restaurantId} : Update a restaurant
     * Updates a restaurant by ID.
     *
     * @param restaurantId The ID of the restaurant to update. (required)
     * @param restaurantInput The updated restaurant. (required)
     * @return The updated restaurant. (status code 200)
     */
    @Operation(
        operationId = "updateRestaurantById",
        summary = "Update a restaurant",
        description = "Updates a restaurant by ID.",
        tags = { "Restaurants" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The updated restaurant.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/restaurants/{restaurantId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Restaurant> updateRestaurantById(
        @Min(1) @Parameter(name = "restaurantId", description = "The ID of the restaurant to update.", required = true, in = ParameterIn.PATH) @PathVariable("restaurantId") Integer restaurantId,
        @Parameter(name = "RestaurantInput", description = "The updated restaurant.", required = true) @Valid @RequestBody RestaurantInput restaurantInput
    );

}
