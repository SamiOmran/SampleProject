package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepo extends JpaRepository<Offers, Long> {
    List<Offers> findAllByRestaurantsId(Long restaurantId);

}
