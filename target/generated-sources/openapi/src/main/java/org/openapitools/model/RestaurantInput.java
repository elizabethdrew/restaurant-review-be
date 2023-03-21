package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * RestaurantInput
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-21T14:40:25.089212Z[Europe/London]")
public class RestaurantInput {

  @JsonProperty("name")
  private String name;

  @JsonProperty("city")
  private String city;

  @JsonProperty("rating")
  private Integer rating;

  public RestaurantInput name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the restaurant.
   * @return name
  */
  @NotNull 
  @Schema(name = "name", description = "The name of the restaurant.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RestaurantInput city(String city) {
    this.city = city;
    return this;
  }

  /**
   * The city where the restaurant is located.
   * @return city
  */
  @NotNull 
  @Schema(name = "city", description = "The city where the restaurant is located.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public RestaurantInput rating(Integer rating) {
    this.rating = rating;
    return this;
  }

  /**
   * The rating of the restaurant.
   * minimum: 1
   * maximum: 5
   * @return rating
  */
  @NotNull @Min(1) @Max(5) 
  @Schema(name = "rating", description = "The rating of the restaurant.", requiredMode = Schema.RequiredMode.REQUIRED)
  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantInput restaurantInput = (RestaurantInput) o;
    return Objects.equals(this.name, restaurantInput.name) &&
        Objects.equals(this.city, restaurantInput.city) &&
        Objects.equals(this.rating, restaurantInput.rating);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, city, rating);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantInput {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
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

