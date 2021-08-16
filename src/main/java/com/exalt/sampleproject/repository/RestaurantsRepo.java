package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Restaurants;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsRepo extends JpaRepository<Restaurants, Long> {
}
