package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * User
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-27T14:02:48.130213+01:00[Europe/London]")
public class User {

  @JsonProperty("id")
  private Long id;

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
  private UserRoleEnum userRole;

  @JsonProperty("created_at")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  public User id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the user.
   * @return id
  */
  
  @Schema(name = "id", description = "The unique identifier of the user.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User name(String name) {
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

  public User email(String email) {
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

  public User username(String username) {
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

  public User password(String password) {
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

  public User userRole(UserRoleEnum userRole) {
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

  public User createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * The date and time the user was created.
   * @return createdAt
  */
  @Valid 
  @Schema(name = "created_at", description = "The date and time the user was created.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.name, user.name) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.username, user.username) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.userRole, user.userRole) &&
        Objects.equals(this.createdAt, user.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, username, password, userRole, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userRole: ").append(toIndentedString(userRole)).append("\n");
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

