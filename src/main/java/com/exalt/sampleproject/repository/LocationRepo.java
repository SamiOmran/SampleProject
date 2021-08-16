package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {
}
