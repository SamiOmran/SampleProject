package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.RestaurantsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    private final RestaurantsRepo restaurantsRepo;
    private final LocationsService locationsService;
    private final ContactsService contactsService;
//    private final static Logger logger = LoggerFactory.getLogger(RestaurantsService.class);
    private ResponseMessage responseMessage = new ResponseMessage();

    public RestaurantsService(RestaurantsRepo restaurantsRepo, LocationsService locationsService, ContactsService contactsService) {
        this.restaurantsRepo = restaurantsRepo;
        this.locationsService = locationsService;
        this.contactsService = contactsService;
    }

    public ResponseMessage save(Restaurants restaurant) {
        restaurantsRepo.save(restaurant);

        responseMessage.setMessage("Successfully restaurant saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public List<Restaurants> findAll() {
        return restaurantsRepo.findAll();
    }

    public ResponseMessage createRestaurant2(AllData allData) {
        Restaurants restaurant = new Restaurants();
        restaurant.setName(allData.getName());
        responseMessage = save(restaurant);

        List<Contacts> contactsList = allData.getContacts();
        contactsService.createContact2(contactsList, restaurant);

        return responseMessage;
    }

    public ResponseMessage createRestaurant(AllData allData) {
        Restaurants restaurant = new Restaurants();
        restaurant.setName(allData.getName());
        responseMessage = save(restaurant);

        List<Locations> locations = allData.getLocations();
        Optional<Restaurants> optionalRestaurants = Optional.of(restaurant);
        locationsService.createLocation(locations, optionalRestaurants);

        return responseMessage;
    }

    public ResponseMessage updateRestaurant(Long id, Restaurants updatedRestaurant) {
        Optional<Restaurants> optionalRestaurants = findById(id);

        if (optionalRestaurants.isPresent()) {
            optionalRestaurants.map(restaurant -> {
                restaurant.setName(updatedRestaurant.getName());
                return responseMessage = save(restaurant);
            });
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

    public AllData getRestaurantsInfo(Long id) {
        Optional<Restaurants> optionalRestaurant = findById(id);

        if (optionalRestaurant.isPresent()) {
            AllData allData = new AllData();
            allData.setName(optionalRestaurant.get().getName());

            List<Locations> locationsList = locationsService.findLocationByRestaurantId(id);
            allData.setLocations(locationsList);

            List<Contacts> contactsList = new ArrayList<>();
            locationsList.forEach(location -> {
                Optional<Locations> optionalLocation = Optional.of(location);
                contactsList.addAll(contactsService.findContactsByLocation(optionalLocation));
            });
            allData.setContacts(contactsList);
            return allData;
        }
       return null;
    }

}
