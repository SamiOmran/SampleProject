package com.exalt.sampleproject.service;

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

    public LocationsService(LocationsRepo locationsRepo) {
        this.locationsRepo = locationsRepo;
    }

    public List<Locations> findLocationByRestaurantId(Long restaurantId) {
        return locationsRepo.findAllByRestaurantId(restaurantId);
    }

    public Optional<Locations> findById(Long locationId) {
        return locationsRepo.findById(locationId);
    }

    public List<Locations> findAll() {
        return locationsRepo.findAll();
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
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
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
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage deleteLocationByRestaurant(Optional<Restaurants> optionalRestaurant) {
        if (optionalRestaurant.isPresent()) {
            List<Locations> locationsList = findLocationByRestaurantId(optionalRestaurant.get().getId());
            locationsList.forEach(locationsRepo::delete);
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}

