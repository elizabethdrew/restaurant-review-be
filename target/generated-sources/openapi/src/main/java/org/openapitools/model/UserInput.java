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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T14:02:48.130213+01:00[Europe/London]")
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
  public enum UserRoleEnum {
    ADMIN("admin"),
    
    REVIEWER("reviewer");

    private String value;

    UserRoleEnum(String value) {
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
    public static UserRoleEnum fromValue(String value) {
      for (UserRoleEnum b : UserRoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("user_role")
  private UserRoleEnum userRole = UserRoleEnum.REVIEWER;

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

  public UserInput userRole(UserRoleEnum userRole) {
    this.userRole = userRole;
    return this;
  }

  /**
   * The role of the user (admin or reviewer)
   * @return userRole
  */
  @NotNull 
  @Schema(name = "user_role", description = "The role of the user (admin or reviewer)", requiredMode = Schema.RequiredMode.REQUIRED)
  public UserRoleEnum getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRoleEnum userRole) {
    this.userRole = userRole;
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
        Objects.equals(this.userRole, userInput.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, username, password, userRole);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInput {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userRole: ").append(toIndentedString(userRole)).append("\n");
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

