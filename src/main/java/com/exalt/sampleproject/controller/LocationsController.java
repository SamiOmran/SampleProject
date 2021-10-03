package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.JsonLocations;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.LocationsService;
import com.exalt.sampleproject.service.RestaurantsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationsController {
    private final LocationsService locationsService;
    private final RestaurantsService restaurantsService;

    public LocationsController(LocationsService locationsService, RestaurantsService restaurantsService) {
        this.locationsService = locationsService;
        this.restaurantsService = restaurantsService;
    }

    private Optional<Restaurants> findRestaurant(Long restaurantId) {
        return restaurantsService.findById(restaurantId);
    }

    @GetMapping(path = "/restaurants/locations")
    public JsonLocations getLocations() {
        return locationsService.findAll();
    }

    /**
     * list location details for given restaurant
     * @param restaurantId id for the restaurant
     * @return location details
     */
    @GetMapping(path = "/restaurants/{restaurantId}/locations", produces = {"application/json"})
    public JsonLocations listLocationByRestaurantId(@PathVariable Long restaurantId) {
        JsonLocations jsonLocations = locationsService.findLocationByRestaurantId(restaurantId);

        return jsonLocations;
    }

    /**
     * @param locations new location object to be created
     * @param restaurantId id for the restaurant
     * @return message status
     */
    @PostMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage createLocation(@RequestBody List<Locations> locations, @PathVariable Long restaurantId) {
        return locationsService.createLocation(locations, findRestaurant(restaurantId));
    }

    /**
     *
     * @param locations the updated one
     * @param restaurantId id for the restaurant to de edited
     * @return message status
     */
    @PutMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage updateContact(@RequestBody List<Locations> locations, @PathVariable Long restaurantId) {
        return locationsService.updateLocationByRestaurant(findRestaurant(restaurantId), locations);
    }

    /**
     *
     * @param restaurantId for location to be deleted
     * @return message status
     */
    @DeleteMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage deleteLocation(@PathVariable Long restaurantId) {
        return locationsService.deleteLocationByRestaurant(findRestaurant(restaurantId));
    }


}
