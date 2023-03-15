package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "RESTAURANT")
@Getter
@Setter
public class RestaurantModel {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "CREATED_AT")
    private OffsetDateTime createdAt;

    protected RestaurantModel(){}

    public RestaurantModel(String name,String city, Integer rating) {
        this.name = name;
        this.city = city;
        this.rating = rating;
    }


}
