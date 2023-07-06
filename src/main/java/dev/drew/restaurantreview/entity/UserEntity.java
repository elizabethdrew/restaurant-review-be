package dev.drew.restaurantreview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.openapitools.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name="password")
    private String password;

    @Column(name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @NotNull
    @Column(name = "role")
    private User.RoleEnum role;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public UserEntity() {
    }

    public UserEntity(Long id, String name, String email, String username, String password, OffsetDateTime createdAt, User.RoleEnum role, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User.RoleEnum getRole() {
        return role;
    }

    public void setRole(User.RoleEnum role) {
        this.role = role;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(createdAt, that.createdAt) &&
                role == that.role &&
                isDeleted == that.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, username, password, createdAt, role, isDeleted);
    }
}
