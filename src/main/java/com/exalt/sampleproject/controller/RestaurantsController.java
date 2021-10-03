package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.JsonAllData;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.RestaurantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestaurantsController {
    private final RestaurantsService restaurantsService;
    //Logger logger = LoggerFactory.getLogger(RestaurantsController.class);

    public RestaurantsController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    /**
     * @return list of Restaurants in DB
     */
    @GetMapping(path = "/restaurants", produces = {"application/json"})
    public JsonAllData listRestaurants() {
        return restaurantsService.getAllRestaurants();
    }

    /**
     * @param stringId restaurant
     * @return restaurant info (name, location, contacts)
     */
    @GetMapping(path = "/restaurants/{stringId}")
    public Object getRestaurantData(@PathVariable String stringId) {
        return restaurantsService.getRestaurantsInfo(stringId);
    }

    /**
     * @param allData new restaurant to be added
     * @return response status
     */
    @PostMapping(path = "/restaurants", produces = {"application/json"})
    public ResponseMessage createRestaurant(@RequestBody AllData allData) {
        return restaurantsService.createRestaurant(allData);
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


    /**
     * create Post method to create resources from uploaded json file
     * @param fileData json file contains data
     * @return response message holds status
     */
    @PostMapping(path = "/restaurants/file")
    public ResponseMessage createRestaurantUsingFile(MultipartFile fileData) {
        return restaurantsService.createRestaurantUsingFile(fileData);
    }

}
