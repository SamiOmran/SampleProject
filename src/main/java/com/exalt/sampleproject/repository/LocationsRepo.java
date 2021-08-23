package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationsRepo extends JpaRepository<Locations, Long> {
    Optional<Locations> findLocationByRestaurantId(Long restaurantId);
    List<Locations> findAllByRestaurantId(Long restaurantId);

}
