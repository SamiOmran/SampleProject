package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepo extends JpaRepository<Items, Long> {
    public List<Items> findAllByRestaurantsId(Long restaurantId);

}
