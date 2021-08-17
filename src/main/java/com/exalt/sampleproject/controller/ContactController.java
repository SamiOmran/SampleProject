package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.ContactService;
import com.exalt.sampleproject.service.RestaurantsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ContactController {
    private final ContactService contactService;
    private final RestaurantsService restaurantsService;

    public ContactController(ContactService contactService, RestaurantsService restaurantsService) {
        this.contactService = contactService;
        this.restaurantsService = restaurantsService;
    }
    
    private Optional<Restaurants> findRestaurant(Long restaurantId) {
        return restaurantsService.findById(restaurantId);
    }

    /**
     * list contact details for given restaurant
     * @param restaurantId id for the restaurant
     * @return contact details
     */
    @GetMapping(path = "/restaurants/{restaurantId}/contacts", produces = {"application/json"})
    public Contact listContactsByRestaurantId(@PathVariable Long restaurantId) {
        if (findRestaurant(restaurantId).isPresent()) {
            Optional<Restaurants> optionalRestaurant = findRestaurant(restaurantId);
            return optionalRestaurant.get().getContact();
        } else {
            throw new ResourceNotFoundException("Restaurant id " + restaurantId + ", not found");
        }
    }

    /**
     *
     * @param contact new contact object to be created
     * @param restaurantId id for the restaurant
     * @return contact created
     */
    @PostMapping(path = "restaurants/{restaurantId}/contacts", produces = {"application/json"})
    public ResponseMessage createContact(@RequestBody Contact contact, @PathVariable Long restaurantId) {
        return contactService.createContact(contact, findRestaurant(restaurantId));
    }

    @PutMapping(path = "restaurants/{restaurantId}/contacts", produces = {"application/json"})
    public ResponseMessage updateContact(@RequestBody Contact contact, @PathVariable Long restaurantId) {
        return contactService.updateContactByRestuarant(findRestaurant(restaurantId), contact);
    }

    @DeleteMapping(path = "restaurants/{restaurantId}/contacts", produces = {"application/json"})
    public ResponseMessage deleteContact(@PathVariable Long restaurantId) {
        return contactService.deleteContactByRestaurant(findRestaurant(restaurantId));
    }


}
