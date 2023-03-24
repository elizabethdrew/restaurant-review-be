package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.Error;
import org.openapitools.model.Restaurant;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * RestaurantResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-24T13:54:03.068402Z[Europe/London]")
public class RestaurantResponse {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("restaurant")
  private Restaurant restaurant;

  @JsonProperty("error")
  private Error error;

  public RestaurantResponse success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
  */
  
  @Schema(name = "success", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public RestaurantResponse restaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
    return this;
  }

  /**
   * Get restaurant
   * @return restaurant
  */
  @Valid 
  @Schema(name = "restaurant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public RestaurantResponse error(Error error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
  */
  @Valid 
  @Schema(name = "error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantResponse restaurantResponse = (RestaurantResponse) o;
    return Objects.equals(this.success, restaurantResponse.success) &&
        Objects.equals(this.restaurant, restaurantResponse.restaurant) &&
        Objects.equals(this.error, restaurantResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, restaurant, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantResponse {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    restaurant: ").append(toIndentedString(restaurant)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

