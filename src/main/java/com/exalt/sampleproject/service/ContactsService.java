package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonContacts;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ContactsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactsService {
    private final ContactsRepo contactsRepo;
    private final LocationsService locationsService;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private final static Logger logger = LoggerFactory.getLogger(ContactsService.class);

    public ContactsService(ContactsRepo contactsRepo, LocationsService locationsService) {
        this.contactsRepo = contactsRepo;
        this.locationsService = locationsService;
    }

    public JsonContacts findAll() {
        List<Contacts> contactsList = contactsRepo.findAll();
        JsonContacts jsonContacts = new JsonContacts();
        if (contactsList.isEmpty()) {
            jsonContacts.setMessage("No contacts Available");
        } else {
            jsonContacts.setContactsList(contactsList);
            jsonContacts.setMessage("Success getting contacts");
        }
        return jsonContacts;
    }

    public List<Contacts> findContactsByLocation(Optional<Locations> optionalLocation) {
        return contactsRepo.findAllByLocation(optionalLocation.get());
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
                contacts.forEach(this::save);
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
        List<Locations> locationsList = locationsService.findLocationByRestaurantId(restaurantId);
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

    public Contacts findById(Long contactId) {
        Optional<Contacts> optionalContacts = contactsRepo.findById(contactId);
        return optionalContacts.get();
    }
}
