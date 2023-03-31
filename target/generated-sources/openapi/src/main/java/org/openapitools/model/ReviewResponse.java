package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.Error;
import org.openapitools.model.Review;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * ReviewResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-31T13:15:36.089055+01:00[Europe/London]")
public class ReviewResponse {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("Review")
  private Review review;

  @JsonProperty("error")
  private Error error;

  public ReviewResponse success(Boolean success) {
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

  public ReviewResponse review(Review review) {
    this.review = review;
    return this;
  }

  /**
   * Get review
   * @return review
  */
  @Valid 
  @Schema(name = "Review", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public ReviewResponse error(Error error) {
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
    ReviewResponse reviewResponse = (ReviewResponse) o;
    return Objects.equals(this.success, reviewResponse.success) &&
        Objects.equals(this.review, reviewResponse.review) &&
        Objects.equals(this.error, reviewResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, review, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewResponse {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    review: ").append(toIndentedString(review)).append("\n");
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

