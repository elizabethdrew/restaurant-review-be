package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.dto.CuisineDto;
import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuisines")
public class CuisineController {

    @Autowired
    private CuisineService cuisineService;

    @GetMapping
    public ResponseEntity<List<String>> getAllCuisines() {
        List<String> cuisineNames = cuisineService.getAllCuisineNames();
        return new ResponseEntity<>(cuisineNames, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CuisineEntity> addNewCuisine(
            @RequestBody CuisineDto cuisineDto) {
        CuisineEntity cuisine = cuisineService.addCuisine(cuisineDto.getName());
        return new ResponseEntity<>(cuisine, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCuisine(@PathVariable String name) {
        cuisineService.deleteCuisine(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
