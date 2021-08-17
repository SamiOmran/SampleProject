package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ContactRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepo contactRepo;

    public ContactService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public ResponseMessage save(Contact contact) {
        contactRepo.save(contact);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Successfully saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public ResponseMessage createContact(Contact contact, Optional<Restaurants> optionalRestaurant) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (optionalRestaurant.isPresent()) {
            optionalRestaurant.map(restaurant -> {
            contact.setRestaurant(restaurant);
            return save(contact);});
            responseMessage.setMessage("Successfully created");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage updateContactByRestuarant(Optional<Restaurants> optionalRestaurant, Contact contact) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (optionalRestaurant.isPresent()) {
            optionalRestaurant.map(restaurant -> {
                contact.setRestaurant(restaurant);
                return save(contact);});
            responseMessage.setMessage("Successfully updated");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage deleteContactByRestaurant(Optional<Restaurants> optionalRestaurant) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (optionalRestaurant.isPresent()) {
            contactRepo.delete(optionalRestaurant.get().getContact());
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("RestaurantId " + optionalRestaurant.get().getId() + ", not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}
