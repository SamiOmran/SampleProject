package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonContacts;
import com.exalt.sampleproject.dto.JsonLocations;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ContactsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactsService {
    private final ContactsRepo contactsRepo;
    private final LocationsService locationsService;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private final static String SUCCESS_MESSAGE = "Success getting the contacts";
    private final static String FAIL_MESSAGE = "No contacts available";
    //private final static Logger logger = LoggerFactory.getLogger(ContactsService.class);

    public ContactsService(ContactsRepo contactsRepo, LocationsService locationsService) {
        this.contactsRepo = contactsRepo;
        this.locationsService = locationsService;
    }

    public JsonContacts findAll() {
        List<Contacts> contactsList = contactsRepo.findAll();
        String message = (contactsList.isEmpty())? FAIL_MESSAGE : SUCCESS_MESSAGE;

        return new JsonContacts(message, contactsList);
    }

    public JsonContacts findById(Long contactId) {
        Optional<Contacts> optionalContacts = contactsRepo.findById(contactId);
        JsonContacts jsonContacts;

        if (optionalContacts.isPresent()) {
            List<Contacts> contactsList = new ArrayList<>();
            contactsList.add(optionalContacts.get());
            jsonContacts = new JsonContacts(SUCCESS_MESSAGE, contactsList);
        } else {
            jsonContacts = new JsonContacts(FAIL_MESSAGE, null);
        }
        return jsonContacts;
    }

    public JsonContacts findContactsByLocation(Optional<Locations> optionalLocation) {
        List<Contacts> contactsList = contactsRepo.findAllByLocation(optionalLocation.get());
        String message = (contactsList.isEmpty())? FAIL_MESSAGE : SUCCESS_MESSAGE;

        return new JsonContacts(message, contactsList);
    }

    public ResponseMessage save(Contacts contacts) {
        contactsRepo.save(contacts);
        responseMessage.setMessage("Successfully saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public void createContact2(List<Contacts> newContacts, Restaurants restaurant ) {
        newContacts.forEach(contact -> {
            save(contact);
            locationsService.createLocation2(contact.getLocation(), restaurant);
        });
    }

    public ResponseMessage createContact(List<Contacts> contacts, Optional<Locations> optionalLocation) {
        if (optionalLocation.isPresent()) {
            contacts.forEach(contact -> {
                contact.setLocation(optionalLocation.get());
                save(contact);
            });
        } else {
            responseMessage.setMessage("Could not create new contact");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public ResponseMessage updateContactByLocation(Optional<Locations> optionalLocation, List<Contacts> contacts) {
        if (optionalLocation.isPresent()) {
            optionalLocation.map(location -> {
                contacts.forEach(contact -> {
                    contact.setLocation(location);
                    save(contact);
                });
                return contacts;
            });
        } else {
            responseMessage.setMessage("Could not update the contact details.");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    @Transactional
    public ResponseMessage deleteContactByLocation(Optional<Locations> optionalLocation) {
        if (optionalLocation.isPresent()) {
            contactsRepo.deleteContactByLocation(optionalLocation.get());
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Could not delete contact");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteContact(Long restaurantId) {
        JsonLocations jsonLocations = locationsService.findLocationByRestaurantId(restaurantId);
        List<Locations> locationsList = jsonLocations.getLocationsList();

        if (!locationsList.isEmpty()) {
            locationsList.forEach(contactsRepo::deleteContactByLocation);
            responseMessage.setMessage("Successfully deleted");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Could not delete contact");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

}
