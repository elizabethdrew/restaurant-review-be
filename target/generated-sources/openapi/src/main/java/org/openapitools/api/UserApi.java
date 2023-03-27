/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.Error;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T14:02:48.130213+01:00[Europe/London]")
@Validated
@Tag(name = "user", description = "the user API")
public interface UserApi {

    /**
     * POST /user : Add a new user
     * Adds a new user to the database.
     *
     * @param userInput The user to add. (required)
     * @return The new user (status code 201)
     *         or Invalid input (status code 400)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "addUser",
        summary = "Add a new user",
        description = "Adds a new user to the database.",
        tags = { "User" },
        responses = {
            @ApiResponse(responseCode = "201", description = "The new user", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
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
        value = "/user",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<UserResponse> addUser(
        @Parameter(name = "UserInput", description = "The user to add.", required = true) @Valid @RequestBody UserInput userInput
    );


    /**
     * DELETE /user/{userId} : Delete a user by ID
     *
     * @param userId The ID or name of the user to retrieve. (required)
     * @return User deleted (status code 204)
     *         or User not found (status code 404)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "deleteUserById",
        summary = "Delete a user by ID",
        tags = { "User" },
        responses = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
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
        value = "/user/{userId}",
        produces = { "application/json" }
    )
    ResponseEntity<Void> deleteUserById(
        @Min(1) @Parameter(name = "userId", description = "The ID or name of the user to retrieve.", required = true, in = ParameterIn.PATH) @PathVariable("userId") Integer userId
    );


    /**
     * GET /user/{userId} : Get a user by ID
     * Returns a single user by ID
     *
     * @param userId The ID or name of the user to retrieve. (required)
     * @return The user (status code 200)
     *         or User not found (status code 404)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "getUserById",
        summary = "Get a user by ID",
        description = "Returns a single user by ID",
        tags = { "User" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The user", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
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
        method = RequestMethod.GET,
        value = "/user/{userId}",
        produces = { "application/json" }
    )
    ResponseEntity<UserResponse> getUserById(
        @Min(1) @Parameter(name = "userId", description = "The ID or name of the user to retrieve.", required = true, in = ParameterIn.PATH) @PathVariable("userId") Integer userId
    );


    /**
     * PUT /user/{userId} : Update a user
     * Updates a user by ID.
     *
     * @param userId The ID or name of the user to retrieve. (required)
     * @param userInput The updated user. (required)
     * @return The updated user (status code 200)
     *         or Invalid input (status code 400)
     *         or User not found (status code 404)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "updateUserById",
        summary = "Update a user",
        description = "Updates a user by ID.",
        tags = { "User" },
        responses = {
            @ApiResponse(responseCode = "200", description = "The updated user", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
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
        method = RequestMethod.PUT,
        value = "/user/{userId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<UserResponse> updateUserById(
        @Min(1) @Parameter(name = "userId", description = "The ID or name of the user to retrieve.", required = true, in = ParameterIn.PATH) @PathVariable("userId") Integer userId,
        @Parameter(name = "UserInput", description = "The updated user.", required = true) @Valid @RequestBody UserInput userInput
    );

}
