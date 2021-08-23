package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.repository.ContactsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactsService {
    private final ContactsRepo contactsRepo;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private final static Logger logger = LoggerFactory.getLogger(ContactsService.class);

    public ContactsService(ContactsRepo contactsRepo) {
        this.contactsRepo = contactsRepo;
    }

    public List<Contacts> findAll() {
        return contactsRepo.findAll();
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
}
