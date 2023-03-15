package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "RESTAURANT")
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @Column(name="ID")
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "CREATED_AT")
    private OffsetDateTime createdAt;
}
