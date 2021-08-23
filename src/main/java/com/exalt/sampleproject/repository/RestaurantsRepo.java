package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsRepo extends JpaRepository<Restaurants, Long> {
}
