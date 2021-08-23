package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ItemsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemsService {
    private final ItemsRepo itemsRepo;
    private final RestaurantsService restaurantsService;
    private final ResponseMessage responseMessage = new ResponseMessage();

    public ItemsService(ItemsRepo itemsRepo, RestaurantsService restaurantsService) {
        this.itemsRepo = itemsRepo;
        this.restaurantsService = restaurantsService;
    }

    public Optional<Items> findById(Long itemId) {
        return itemsRepo.findById(itemId);
    }

    public ResponseMessage save(Items item) {
        itemsRepo.save(item);
        responseMessage.setMessage("Success saving new item");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public ResponseMessage createItem(Items newItem, Long restaurantId) {
        Optional<Restaurants> optionalRestaurants = restaurantsService.findById(restaurantId);

        if (optionalRestaurants.isPresent()) {
            newItem.setRestaurants(optionalRestaurants.get());
            return save(newItem);
        } else {
            responseMessage.setMessage("Couldn't save new item");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public List<Items> findItemsByRestaurantId(Long restaurantId) {
        List<Items> itemsList = itemsRepo.findAllByRestaurantsId(restaurantId);

        if (!itemsList.isEmpty()) {
            itemsList.forEach(System.out::println);
            return itemsList;
        }
        throw new NoSuchElementException();
    }

    public ResponseMessage updateItem(Long restaurantId, Long itemId, Items newItem) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);
        Optional<Items> optionalItems = findById(itemId);

        if (optionalRestaurant.isPresent() && optionalItems.isPresent()) {
            optionalItems.map(item-> {
                item.setDescription(newItem.getDescription());
                item.setPrice(newItem.getPrice());
                return save(item);
            });

            responseMessage.setMessage("Success updating item");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Error finding the restaurant or the item");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteItem(Long restaurantId, Long itemId) {
        Optional<Restaurants> optionalRestaurant = restaurantsService.findById(restaurantId);
        Optional<Items> optionalItems = findById(itemId);

        if (optionalRestaurant.isPresent() && optionalItems.isPresent()) {
            itemsRepo.delete(optionalItems.get());
            responseMessage.setMessage("Success deleting itemId + " + optionalItems.get().getId());
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Error in deleting item " + optionalItems.get().getId());
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }
}
