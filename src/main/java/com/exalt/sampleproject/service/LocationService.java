package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Location;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.LocationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepo locationRepo;
    private final ResponseMessage responseMessage = new ResponseMessage();

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public ResponseMessage save(Location location) {
        locationRepo.save(location);

        responseMessage.setMessage("Successfully saved");
        responseMessage.setStatus(1);
        return responseMessage;
    }

    public ResponseMessage createLocation(List<Location> locations, Optional<Restaurants> optionalRestaurant) {
        if (optionalRestaurant.isPresent()) {
            locations.forEach(location -> {
                optionalRestaurant.map(restaurant -> {
                    location.setRestaurant(restaurant);
                    return save(location);});
            });
            responseMessage.setMessage("Successfully created");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage updateLocationByRestuarant(Optional<Restaurants> optionalRestaurant, List<Location> locations) {
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
            locationRepo.delete(optionalRestaurant.get().getLocations().get(0));
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}

