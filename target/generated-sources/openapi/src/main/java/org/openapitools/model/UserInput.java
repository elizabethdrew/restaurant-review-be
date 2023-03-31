package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * UserInput
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-31T14:18:31.149766+01:00[Europe/London]")
public class UserInput {

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  /**
   * The role of the user (admin or reviewer)
   */
  public enum RoleEnum {
    ADMIN("ADMIN"),
    
    REVIEWER("REVIEWER");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("role")
  private RoleEnum role = RoleEnum.REVIEWER;

  public UserInput name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the user.
   * @return name
  */
  @NotNull 
  @Schema(name = "name", description = "The name of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserInput email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The email address of the user.
   * @return email
  */
  @NotNull 
  @Schema(name = "email", description = "The email address of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserInput username(String username) {
    this.username = username;
    return this;
  }

  /**
   * The username of the user.
   * @return username
  */
  @NotNull 
  @Schema(name = "username", description = "The username of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserInput password(String password) {
    this.password = password;
    return this;
  }

  /**
   * The password of the user.
   * @return password
  */
  @NotNull 
  @Schema(name = "password", description = "The password of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserInput role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the user (admin or reviewer)
   * @return role
  */
  @NotNull 
  @Schema(name = "role", description = "The role of the user (admin or reviewer)", requiredMode = Schema.RequiredMode.REQUIRED)
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInput userInput = (UserInput) o;
    return Objects.equals(this.name, userInput.name) &&
        Objects.equals(this.email, userInput.email) &&
        Objects.equals(this.username, userInput.username) &&
        Objects.equals(this.password, userInput.password) &&
        Objects.equals(this.role, userInput.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, username, password, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInput {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

