package com.exalt.sampleproject.service;

import com.exalt.sampleproject.model.Location;
import com.exalt.sampleproject.repository.LocationRepo;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location save(Location location) {
        return locationRepo.save(location);
    }


}
