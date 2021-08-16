package com.exalt.sampleproject.service;

import com.exalt.sampleproject.model.Contact;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactRepo contactRepo;

    public ContactService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }
}
