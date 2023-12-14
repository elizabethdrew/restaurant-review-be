package dev.drew.restaurantreview.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Indexed
@Table(name = "restaurant")
public class RestaurantEntity extends org.openapitools.model.Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @FullTextField
    @NotNull
    @Column(name = "name")
    private String name;

    @FullTextField
    @NotNull
    @Column(name = "city")
    private String city;

    @Min(1)
    @Max(3)
    @Column(name = "price_range")
    private Integer priceRange;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "restaurant_cuisine",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
    private List<CuisineEntity> cuisines = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Min(1)
    @Max(5)
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Column(name = "image_url")
    private String imageUrl;

    public RestaurantEntity() {
    }

    public RestaurantEntity(Long id, String name, String city, UserEntity user, Integer priceRange, Double latitude, Double longitude, List<CuisineEntity> cuisines, Integer rating, OffsetDateTime createdAt, Boolean isDeleted, UserEntity owner) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.user = user;
        this.priceRange = priceRange;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisines = cuisines;
        this.rating = rating;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<CuisineEntity> getRestaurantCuisines() {
        return cuisines;
    }

    public void setRestaurantCuisines(List<CuisineEntity> cuisines) {
        this.cuisines = cuisines;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getPriceRange(), that.getPriceRange()) && Objects.equals(getLatitude(), that.getLatitude()) && Objects.equals(getLongitude(), that.getLongitude()) && Objects.equals(getCuisines(), that.getCuisines()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getRating(), that.getRating()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getIsDeleted(), that.getIsDeleted() && Objects.equals(getOwner(), that.getOwner()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName(), getCity(), getPriceRange(), getLatitude(), getLongitude(), getCuisines(), getUser(), getRating(), getCreatedAt(), getIsDeleted(), getOwner());
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", priceRange=" + priceRange +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", user=" + user +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", isDeleted=" + isDeleted +
                ", owner=" + owner +
                '}';
    }
}