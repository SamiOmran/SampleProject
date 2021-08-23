package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.ItemsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ResponseMessage save(Items item) {
        itemsRepo.save(item);
        responseMessage.setMessage("Success item saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public List<Items> findAll() {
        return itemsRepo.findAll();
    }

    public Optional<Items> findById(Long itemId) {
        return itemsRepo.findById(itemId);
    }

    public List<Items> findItemsByRestaurantId(Long restaurantId) {
        List<Items> itemsList = itemsRepo.findAllByRestaurantsId(restaurantId);

        if (!itemsList.isEmpty()) {
            itemsList.forEach(System.out::println);
            return itemsList;
        }
        throw new NoSuchElementException();
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
        Optional<Items> optionalItems = findById(itemId);

        if (optionalRestaurant.isPresent() && optionalItems.isPresent()) {
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
        Optional<Items> optionalItems = findById(itemId);

        if (optionalRestaurant.isPresent() && optionalItems.isPresent()) {
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
