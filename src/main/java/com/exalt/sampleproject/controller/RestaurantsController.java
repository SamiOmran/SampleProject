package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.ContactsService;
import com.exalt.sampleproject.service.LocationsService;
import com.exalt.sampleproject.service.RestaurantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantsController {
    private final RestaurantsService restaurantsService;
    private final LocationsService locationsService;
    private final static Logger logger = LoggerFactory.getLogger(RestaurantsController.class);

    public RestaurantsController(RestaurantsService restaurantsService, LocationsService locationsService, ContactsService contactsService) {
        this.restaurantsService = restaurantsService;
        this.locationsService = locationsService;
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
        Restaurants restaurant = new Restaurants();
        restaurant.setName(allData.getName());
        restaurantsService.save(restaurant);

        List<Locations> locations = allData.getLocations();
        Optional<Restaurants> optionalRestaurants = Optional.of(restaurant);
        locationsService.createLocation(locations, optionalRestaurants);

        logger.info("Successfully created restaurant: " + restaurant.getName());
        return restaurantsService.save(restaurant);
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








}
