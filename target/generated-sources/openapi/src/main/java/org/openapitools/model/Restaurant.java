package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Restaurant
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T12:40:33.691456+01:00[Europe/London]")
public class Restaurant {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("city")
  private String city;

  @JsonProperty("rating")
  private Integer rating;

  @JsonProperty("created_at")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  public Restaurant id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the restaurant.
   * @return id
  */
  
  @Schema(name = "id", description = "The unique identifier of the restaurant.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Restaurant name(String name) {
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

  public Restaurant city(String city) {
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

  public Restaurant rating(Integer rating) {
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

  public Restaurant createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * The date and time the restaurant was created.
   * @return createdAt
  */
  @Valid 
  @Schema(name = "created_at", description = "The date and time the restaurant was created.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Restaurant restaurant = (Restaurant) o;
    return Objects.equals(this.id, restaurant.id) &&
        Objects.equals(this.name, restaurant.name) &&
        Objects.equals(this.city, restaurant.city) &&
        Objects.equals(this.rating, restaurant.rating) &&
        Objects.equals(this.createdAt, restaurant.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, city, rating, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Restaurant {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

