package com.exalt.sampleproject.service;

import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Location;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.RestaurantsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    private final RestaurantsRepo restaurantsRepo;
    private final LocationService locationService;
    private final ContactService contactService;

    Logger logger = LoggerFactory.getLogger(RestaurantsService.class);

    public RestaurantsService(RestaurantsRepo restaurantsRepo, LocationService locationService, ContactService contactService) {
        this.restaurantsRepo = restaurantsRepo;
        this.locationService = locationService;
        this.contactService = contactService;
    }


    @Autowired
    public void createRecord() {
        Restaurants res = new Restaurants("KAN YA MAKAN");
        Restaurants res2 = new Restaurants("ward");

        Location location = new Location("nablus", "rafidya", "akadimya", res);
        Contact contact = new Contact("092345125", "0597430457", res);
        locationService.save(location);
        contactService.save(contact);

        res.addContact(contact);
        res.addLocation(location);
        save(res);
        save(res2);
    }

    public Restaurants save(Restaurants restaurants) {
        return restaurantsRepo.save(restaurants);
    }

    public List<Restaurants> findAll() {
        return restaurantsRepo.findAll();
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public Restaurants updateRestaurant(Long id, Restaurants updatedRestaurant) {

        return findById(id)
                .map(restaurant -> {
                    restaurant.setName(updatedRestaurant.getName());
                    restaurant.setContacts(updatedRestaurant.getContacts());
                    restaurant.setLocations(updatedRestaurant.getLocations());
                    return restaurantsRepo.save(restaurant);
                })
                .orElseGet(() -> {
                    return restaurantsRepo.save(updatedRestaurant);
                });
    }

    public void deleteRestaurant(Long id) {
        restaurantsRepo.delete(findById(id).get());
    }
}
