package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Long> {
}
