package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonLocations;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.LocationsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationsService {
    private final LocationsRepo locationsRepo;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private static final String SUCCESS_MESSAGE = "Success getting locations.";
    private static final String FAIL_MESSAGE = "No locations found.";

    public LocationsService(LocationsRepo locationsRepo) {
        this.locationsRepo = locationsRepo;
    }

    public JsonLocations findLocationByRestaurantId(Long restaurantId) {
        List<Locations> locationsList = locationsRepo.findAllByRestaurantId(restaurantId);
        String message = (locationsList.isEmpty())? FAIL_MESSAGE : SUCCESS_MESSAGE;

        return new JsonLocations(message, locationsList);
    }

    public Optional<Locations> findById(Long locationId) {
        return locationsRepo.findById(locationId);
    }

    public JsonLocations findAll() {
        List<Locations> locationsList = locationsRepo.findAll();
        String message = (locationsList.isEmpty())? FAIL_MESSAGE : SUCCESS_MESSAGE  ;

        return new JsonLocations(message, locationsList);
    }

    public ResponseMessage save(Locations location) {
        locationsRepo.save(location);

        responseMessage.setMessage("Successfully location saved");
        responseMessage.setStatus(1);
        return responseMessage;
    }

    public void createLocation2(Locations newLocation, Restaurants restaurant) {
        newLocation.setRestaurant(restaurant);
        save(newLocation);
    }

    public ResponseMessage createLocation(List<Locations> locations, Optional<Restaurants> optionalRestaurant) {
        if (optionalRestaurant.isPresent()) {
            locations.forEach(location -> {
                optionalRestaurant.map(restaurant -> {
                    location.setRestaurant(restaurant);
                    return save(location);});
            });
        } else {
            responseMessage.setMessage("RestaurantId was not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage updateLocationByRestaurant(Optional<Restaurants> optionalRestaurant, List<Locations> locations) {
        if (optionalRestaurant.isPresent()) {
            locations.forEach(location -> {
                optionalRestaurant.map(restaurant -> {
                    location.setRestaurant(restaurant);
                    return save(location);});
            });

            responseMessage.setMessage("Successfully updated");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId was not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage deleteLocationByRestaurant(Optional<Restaurants> optionalRestaurant) {
        if (optionalRestaurant.isPresent()) {
            JsonLocations jsonLocations = findLocationByRestaurantId(optionalRestaurant.get().getId());
            List<Locations> locationsList = jsonLocations.getLocationsList();

            locationsList.forEach(locationsRepo::delete);
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId was not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}

