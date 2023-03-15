package org.example.controller;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.RestaurantsApi;
import org.openapitools.client.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController extends RestaurantsApi {

    @Override
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() throws ApiException {
        List<Restaurant> res = new ArrayList<>();
        res.add(new Restaurant());
        return res;
    }
}