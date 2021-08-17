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


    private final static Logger logger = LoggerFactory.getLogger(RestaurantsService.class);

    public RestaurantsService(RestaurantsRepo restaurantsRepo) {
        this.restaurantsRepo = restaurantsRepo;
    }

//
//    public void createRecord() {
//        Restaurants res = new Restaurants("KAN YA MAKAN");
//        Restaurants res2 = new Restaurants("ward");
//
//        Location location = new Location("nablus", "rafidya", "akadimya", res);
//        Contact contact = new Contact("092345125", "0597430457", res);
//
//        res.setContact(contact);
//        res.addLocation(location);
//
//       // logger.info("location: " + res.getLocation().getCity() + " " + res.getLocation().getSection());
//        save(res);
//        save(res2);
//    }

    public ResponseMessage save(Restaurants restaurants) {
        restaurantsRepo.save(restaurants);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Successfully saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public List<Restaurants> findAll() {
        return restaurantsRepo.findAll();
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public ResponseMessage updateRestaurant(Long id, Restaurants updatedRestaurant) {
        ResponseMessage responseMessage = new ResponseMessage();
        Optional<Restaurants> optionalRestaurants = findById(id);

        if (optionalRestaurants.isPresent()) {
            optionalRestaurants.map(restaurant -> {
                restaurant.setName(updatedRestaurant.getName());
                restaurant.setContact(updatedRestaurant.getContact());
                restaurant.setLocations(updatedRestaurant.getLocations());
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
        ResponseMessage responseMessage = new ResponseMessage();

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
