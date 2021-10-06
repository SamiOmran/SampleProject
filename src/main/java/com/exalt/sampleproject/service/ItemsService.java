package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonItems;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ItemsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    private final ItemsRepo itemsRepo;
    private final RestaurantsService restaurantsService;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private static final String SUCCESS_MESSAGE = "Success getting items.";
    private static final String FAIL_MESSAGE = "Failed getting items.";

    public ItemsService(ItemsRepo itemsRepo, RestaurantsService restaurantsService) {
        this.itemsRepo = itemsRepo;
        this.restaurantsService = restaurantsService;
    }

    public ResponseMessage save(Items item) {
        itemsRepo.save(item);
        responseMessage.setMessage("Success item saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public JsonItems findAll() {
        List<Items> itemsList = itemsRepo.findAll();
        boolean isEmpty = itemsList.isEmpty();

        return (isEmpty)? new JsonItems(FAIL_MESSAGE, itemsList) : new JsonItems(SUCCESS_MESSAGE, itemsList);
    }

    public JsonItems findById(Long itemId) {
        Optional<Items> optionalItems = itemsRepo.findById(itemId);
        JsonItems jsonItems;
        if (optionalItems.isPresent()) {
            List<Items> itemsList = new ArrayList<>();
            itemsList.add(optionalItems.get());
            jsonItems = new JsonItems(SUCCESS_MESSAGE, itemsList);
        } else {
            jsonItems = new JsonItems(FAIL_MESSAGE, null);
        }
        return jsonItems;
    }

    public JsonItems findItemsByRestaurantId(Long restaurantId) {
        List<Items> itemsList = itemsRepo.findAllByRestaurantsId(restaurantId);
        boolean isEmpty = itemsList.isEmpty();

        return (isEmpty)? new JsonItems(FAIL_MESSAGE, itemsList) : new JsonItems(SUCCESS_MESSAGE, itemsList);
    }

    public ResponseMessage createItem(String newItem, Long restaurantId, MultipartFile multipartFile) throws IOException {
        Optional<Restaurants> optionalRestaurants = restaurantsService.findById(restaurantId);

        if (optionalRestaurants.isPresent()) {
            Items item = new Items();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                item = objectMapper.readValue(newItem, Items.class);
            } catch (Exception e) {
                System.out.println(e);
            }
            item.setRestaurants(optionalRestaurants.get());

            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];
            int i = 0;

            for (byte b: multipartFile.getBytes()) {
                byteObjects[i++] = b;
            }
            item.setImage(byteObjects);
            return save(item);
        } else {
            responseMessage.setMessage("Couldn't save new item");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage updateItem(Long restaurantId, Long itemId, Items newItem) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);
        JsonItems jsonItems = findById(itemId);
        Optional<Items> optionalItems = Optional.of(jsonItems.getItemsList().get(0));

        if (optionalRestaurant.isPresent()) {
            optionalItems.map(item-> {
                item.setDescription(newItem.getDescription());
                item.setPrice(newItem.getPrice());
                return save(item);
            });
        } else {
            responseMessage.setMessage("Error finding the restaurant or the item");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteItem(Long restaurantId, Long itemId) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);
        JsonItems jsonItems = findById(itemId);
        Optional<Items> optionalItems = Optional.of(jsonItems.getItemsList().get(0));

        if (optionalRestaurant.isPresent()) {
            itemsRepo.delete(optionalItems.get());
            responseMessage.setMessage("Success deleting itemId + " + optionalItems.get().getId());
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Error happened while deleting item ");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

}
