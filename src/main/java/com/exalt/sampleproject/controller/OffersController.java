package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Offers;
import com.exalt.sampleproject.service.OffersSerivce;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OffersController {
    private final OffersSerivce offersSerivce;

    public OffersController(OffersSerivce offersSerivce) {
        this.offersSerivce = offersSerivce;
    }

    @GetMapping(path = "/restaurants/{restaurantId}/offers")
    public List<Offers> getRestaurantOffers(@PathVariable Long restaurantId) {
        return offersSerivce.getRestaurantOffers(restaurantId);
    }

    @GetMapping(path = "/restaurants/offers")
    public List<Offers> getOffers() {
        return offersSerivce.findAll();
    }

    @PostMapping(path = "/restaurants/{restaurantId}/offers")
    public ResponseMessage createOffer(@RequestBody Offers newOffer, @PathVariable Long restaurantId) {
        return offersSerivce.createOffer(newOffer, restaurantId);
    }

    @PutMapping(path = "/restaurants/{restaurantId}/offers/{offerId}")
    public ResponseMessage updateOffer(@RequestBody Offers updatedOffer, @PathVariable Long restaurantId, @PathVariable Long offerId) {
        return offersSerivce.updateOffer(updatedOffer, restaurantId, offerId);
    }
}
