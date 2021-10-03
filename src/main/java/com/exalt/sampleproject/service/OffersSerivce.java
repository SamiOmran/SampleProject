package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonOffers;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Offers;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.OffersRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OffersSerivce {
    private final RestaurantsService restaurantsService;
    private final OffersRepo offersRepo;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private final static String SUCCESS_MESSAGE = "Success getting the offers";
    private final static String FAIL_MESSAGE = "No offers available";

    public OffersSerivce(RestaurantsService restaurantsService, OffersRepo offersRepo) {
        this.restaurantsService = restaurantsService;
        this.offersRepo = offersRepo;
    }

    public ResponseMessage save(Offers offer) {
        offersRepo.save(offer);

        responseMessage.setMessage("Successfully offer saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public JsonOffers getRestaurantOffers(Long restaurantId) {
        List<Offers> offersList = offersRepo.findAllByRestaurantsId(restaurantId);
        JsonOffers jsonOffers;

        if (offersList.isEmpty()) {
            jsonOffers = new JsonOffers(FAIL_MESSAGE, offersList);
        } else {
            jsonOffers = new JsonOffers(SUCCESS_MESSAGE, offersList);
        }
        return jsonOffers;
    }

    public JsonOffers findAll() {
        List<Offers> offersList = offersRepo.findAll();
        JsonOffers jsonOffers;

        if (offersList.isEmpty()) {
            jsonOffers = new JsonOffers(FAIL_MESSAGE, offersList);
        } else {
            jsonOffers = new JsonOffers(SUCCESS_MESSAGE, offersList);
        }
        return jsonOffers;
    }

    public ResponseMessage createOffer(Offers newOffer, Long restaurantId) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);

        if (optionalRestaurant.isPresent()) {
            //newOffer.setLocalDate(LocalDate.now().plusDays);
            newOffer.setRestaurants(optionalRestaurant.get());
            save(newOffer);

            responseMessage.setMessage("Successfully offer saved");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Could not found restaurant");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }


    public ResponseMessage updateOffer(Offers updatedOffer, Long restaurantId, Long offerId) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);
        Optional<Offers> optionalOffer = offersRepo.findById(offerId);

        if (optionalRestaurant.isPresent() && optionalOffer.isPresent()) {
            optionalOffer.map(offer -> {
                offer.setOffer(updatedOffer.getOffer());
                offer.setLocalDate(updatedOffer.getLocalDate());
                offer.setRestaurants(updatedOffer.getRestaurants());

                return save(offer);
            });
        } else {
            responseMessage.setMessage("Could not update offer");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}
