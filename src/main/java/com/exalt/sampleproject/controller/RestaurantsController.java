package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.JsonAllData;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.service.RestaurantsService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@RestController
public class RestaurantsController {
    private final RestaurantsService restaurantsService;
    //Logger logger = LoggerFactory.getLogger(RestaurantsController.class);
    @Autowired MessageSource messageSource;

    public RestaurantsController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    /**
     * @return list of Restaurants in DB
     */
    @GetMapping(path = "/restaurants", produces = {"application/json"})
    public JsonAllData listRestaurants(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return restaurantsService.getAllRestaurants(locale);
    }

    /**
     * @param stringId restaurant
     * @return restaurant info (name, location, contacts)
     */
    @GetMapping(path = "/restaurants/{stringId}")
    public JsonAllData getRestaurantData(@PathVariable String stringId) {
        return restaurantsService.getRestaurantsInfo(stringId);
    }

    /**
     * create Post method to create resources from uploaded json file
     * @param fileData json file contains data
     * @return response message holds status
     */
    @PostMapping(path = "/restaurants/file")
    public ResponseMessage createRestaurantUsingFile(MultipartFile fileData, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return restaurantsService.createRestaurantUsingFile(fileData);
    }

    /**
     * @param allData new restaurant to be added
     * @return response status
     */
    @PostMapping(path = "/restaurants", produces = {"application/json"})
    public ResponseMessage createRestaurant(@RequestBody AllData allData) {
        return restaurantsService.createRestaurant(allData);
    }

    /**
     * @param id of old restaurant
     * @param updatedRestaurant restaurant with updated values
     * @return updatedRestaurant
     */
    @PutMapping(path = "/restaurants/{id}", produces = {"application/json"})
    public ResponseMessage updateRestaurant(@PathVariable Long id, @RequestBody Restaurants updatedRestaurant) {
        return restaurantsService.updateRestaurant(id, updatedRestaurant);
    }

    /**
     * @param id of restaurant to be deleted
     * @return message
     */
    @DeleteMapping(path = "/restaurants/{id}", produces = {"application/json"})
    public ResponseMessage deleteRestaurant(@PathVariable Long id) {
        return restaurantsService.deleteRestaurant(id);
    }

    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
        return messageSource.getMessage("welcome.message", null, locale);
    }
}
