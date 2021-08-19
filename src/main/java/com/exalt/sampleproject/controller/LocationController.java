package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Location;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.LocationService;
import com.exalt.sampleproject.service.RestaurantsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationController {
    private final LocationService locationService;
    private final RestaurantsService restaurantsService;

    public LocationController(LocationService locationService, RestaurantsService restaurantsService) {
        this.locationService = locationService;
        this.restaurantsService = restaurantsService;
    }

    private Optional<Restaurants> findRestaurant(Long restaurantId) {
        return restaurantsService.findById(restaurantId);
    }

    /**
     * list location details for given restaurant
     * @param restaurantId id for the restaurant
     * @return location details
     */
    @GetMapping(path = "/restaurants/{restaurantId}/locations", produces = {"application/json"})
    public List<Location> listLocationByRestaurantId(@PathVariable Long restaurantId) {
        Optional<Restaurants> optionalRestaurant = findRestaurant(restaurantId);

        if (optionalRestaurant.isPresent()) {
            return optionalRestaurant.get().getLocations();
        } else {
            throw new ResourceNotFoundException("Restaurant id " + restaurantId + ", not found");
        }
    }

    /**
     *
     * @param locations new location object to be created
     * @param restaurantId id for the restaurant
     * @return message status
     */
    @PostMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage createLocation(@RequestBody List<Location> locations, @PathVariable Long restaurantId) {
        return locationService.createLocation(locations, findRestaurant(restaurantId));
    }

    /**
     *
     * @param locations the updated one
     * @param restaurantId id for the restaurant to de edited
     * @return message status
     */
    @PutMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage updateContact(@RequestBody List<Location> locations, @PathVariable Long restaurantId) {
        return locationService.updateLocationByRestuarant(findRestaurant(restaurantId), locations);
    }

    /**
     *
     * @param restaurantId for location to be deleted
     * @return message status
     */
    @DeleteMapping(path = "restaurants/{restaurantId}/locations", produces = {"application/json"})
    public ResponseMessage deleteLocation(@PathVariable Long restaurantId) {
        return locationService.deleteLocationByRestaurant(findRestaurant(restaurantId));
    }


}
