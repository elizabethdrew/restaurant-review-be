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
 * ReviewInput
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-31T13:15:36.089055+01:00[Europe/London]")
public class ReviewInput {

  @JsonProperty("restaurant_id")
  private Long restaurantId;

  @JsonProperty("rating")
  private Float rating;

  @JsonProperty("comment")
  private String comment;

  public ReviewInput restaurantId(Long restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }

  /**
   * Get restaurantId
   * @return restaurantId
  */
  @NotNull 
  @Schema(name = "restaurant_id", requiredMode = Schema.RequiredMode.REQUIRED)
  public Long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public ReviewInput rating(Float rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * minimum: 1
   * maximum: 5
   * @return rating
  */
  @NotNull @DecimalMin("1") @DecimalMax("5") 
  @Schema(name = "rating", requiredMode = Schema.RequiredMode.REQUIRED)
  public Float getRating() {
    return rating;
  }

  public void setRating(Float rating) {
    this.rating = rating;
  }

  public ReviewInput comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
  */
  @NotNull 
  @Schema(name = "comment", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewInput reviewInput = (ReviewInput) o;
    return Objects.equals(this.restaurantId, reviewInput.restaurantId) &&
        Objects.equals(this.rating, reviewInput.rating) &&
        Objects.equals(this.comment, reviewInput.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(restaurantId, rating, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewInput {\n");
    sb.append("    restaurantId: ").append(toIndentedString(restaurantId)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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

