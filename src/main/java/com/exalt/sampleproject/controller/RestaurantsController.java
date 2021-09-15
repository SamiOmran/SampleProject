package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.RestaurantsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantsController {
    private final RestaurantsService restaurantsService;

    public RestaurantsController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    /**
     * @return list of Restaurants in DB
     */
    @GetMapping(path = "/restaurants", produces = {"application/json"})
    public List<Restaurants> listRestaurants() {
        return restaurantsService.findAll();
    }

    /**
     * @param id of that restaurant
     * @return specific restaurant
     */
    @GetMapping(path = "/restaurants/{id}", produces = {"application/json"})
    public Restaurants getRestaurant(@PathVariable Long id) {
        Optional<Restaurants> optionalRestaurants =  restaurantsService.findById(id);
        return (optionalRestaurants.isPresent()) ? optionalRestaurants.get() : null;
    }

    /**
     * @param allData new restaurant to be added
     * @return response status
     */
    @PostMapping(path = "/restaurants", produces = {"application/json"})
    public ResponseMessage createRestaurant(/*@RequestBody Restaurants restaurant,*/ @RequestBody AllData allData) {
        //return restaurantsService.createRestaurant(allData);
        return restaurantsService.createRestaurant2(allData);
    }

    /**
     * @param id of old restaurant
     * @param updatedRestaurant restaurant with updated values
     * @return updatedRestaurant
     */
    @PutMapping(path = "/restaurants/{id}", produces = {"application/json"})
    public ResponseMessage updateRestaurant(@PathVariable Long id, @RequestBody Restaurants updatedRestaurant) {
        return restaurantsService.updateRestaurant(id, updatedRestaurant);
    }

    /**
     * @param id of restaurant to be deleted
     * @return message
     */
    @DeleteMapping(path = "/restaurants/{id}", produces = {"application/json"})
    public ResponseMessage deleteRestaurant(@PathVariable Long id) {
        return restaurantsService.deleteRestaurant(id);
    }


    /***
     * @param id restaurant
     * @return restaurant info (name, location, contacts)
     */
    @GetMapping(path = "/restaurants/{id}")
    public AllData getRestaurantsData(@PathVariable Long id) {
        return restaurantsService.getRestaurantsInfo(id);
    }




}
