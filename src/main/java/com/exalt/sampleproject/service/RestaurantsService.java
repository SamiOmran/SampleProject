package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.RestaurantsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    private final RestaurantsRepo restaurantsRepo;
    //private final static Logger logger = LoggerFactory.getLogger(RestaurantsService.class);
    private final ResponseMessage responseMessage = new ResponseMessage();

    public RestaurantsService(RestaurantsRepo restaurantsRepo) {
        this.restaurantsRepo = restaurantsRepo;
    }

    public ResponseMessage save(Restaurants restaurant) {
        restaurantsRepo.save(restaurant);

        responseMessage.setMessage("Successfully saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public List<Restaurants> findAll() {
        return restaurantsRepo.findAll();
    }

    public ResponseMessage updateRestaurant(Long id, Restaurants updatedRestaurant) {
        Optional<Restaurants> optionalRestaurants = findById(id);

        if (optionalRestaurants.isPresent()) {
            optionalRestaurants.map(restaurant -> {
                restaurant.setName(updatedRestaurant.getName());
                save(restaurant);
                return restaurant;
            });
            responseMessage.setMessage("Success updating Restaurant");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + id + ", was not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteRestaurant(Long id) {
        if (findById(id).isPresent()) {
            restaurantsRepo.delete(findById(id).get());
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Not found");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

}
