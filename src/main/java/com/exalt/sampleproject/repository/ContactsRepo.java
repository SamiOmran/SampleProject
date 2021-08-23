package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactsRepo extends JpaRepository<Contacts, Long> {
    List<Contacts> findAll();
    List<Contacts> findAllByLocation(Locations location);
    void deleteContactByLocation(Locations location);
}
