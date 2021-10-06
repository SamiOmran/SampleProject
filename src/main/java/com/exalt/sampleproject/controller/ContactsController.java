package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.JsonContacts;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.service.ContactsService;
import com.exalt.sampleproject.service.LocationsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactsController {
    // fields
    private final ContactsService contactsService;
    private final LocationsService locationsService;

    // Constructor
    public ContactsController(ContactsService contactsService, LocationsService locationsService) {
        this.contactsService = contactsService;
        this.locationsService = locationsService;
    }
    
    private Optional<Locations> findLocation(Long locationId) {
        return locationsService.findById(locationId);
    }

    @GetMapping(path = "/restaurants/contacts/{contactId}", produces = {"application/json"})
    public JsonContacts getContact(@PathVariable Long contactId) {
        return contactsService.findById(contactId);
    }

    @GetMapping(path = "/restaurants/contacts", produces = {"application/json"})
    public JsonContacts getAllContacts() {
        return contactsService.findAll();
    }

    /**
     * list contact details for given restaurant
     * @param locationId id for the restaurant
     * @return contact details
     */
    @GetMapping(path = "/restaurants/{locationId}/contacts", produces = {"application/json"})
    public JsonContacts getContactsForLocation(@PathVariable Long locationId) {
        Optional<Locations> optionalLocation = findLocation(locationId);

        if (optionalLocation.isPresent()) {
            return contactsService.findContactsByLocation(optionalLocation);
        } else {
            throw new ResourceNotFoundException("LocationId " + locationId + ", not found");
        }
    }


    /**
     * @param contacts new contact object to be created
     * @param locationId id for the restaurant
     * @return message status
     */
    @PostMapping(path = "restaurants/{locationId}/contacts", produces = {"application/json"})
    public ResponseMessage createContact(@RequestBody List<Contacts> contacts, @PathVariable Long locationId) {
        return contactsService.createContact(contacts, findLocation(locationId));
    }

    /**
     * @param contacts the updated one
     * @param locationId id for the restaurant to de edited
     * @return message status
     */
    @PutMapping(path = "restaurants/{locationId}/contacts", produces = {"application/json"})
    public ResponseMessage updateContact(@RequestBody List<Contacts> contacts, @PathVariable Long locationId) {
        return contactsService.updateContactByLocation(findLocation(locationId), contacts);
    }

    /**
     * @param locationId for contact to be deleted
     * @return message status
     */
    @DeleteMapping(path = "restaurants/{locationId}/contacts", produces = {"application/json"})
    public ResponseMessage deleteContact(@PathVariable Long locationId) {
        return contactsService.deleteContactByLocation(findLocation(locationId));
    }
}
