package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.service.ItemsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemsController {
    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @GetMapping(path = "/restaurants/items")
    public List<Items> listAllItems() {
        return itemsService.findAll();
    }

    @GetMapping(path = "/restaurants/items/{itemId}")
    public Items getItem(@PathVariable Long itemId) {
        Optional<Items> optionalItem = itemsService.findById(itemId);

        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            throw new ResourceNotFoundException("Could not find item");
        }
    }

    @GetMapping(path = "/restaurants/{restaurantId}/items", produces = {"application/json"})
    public List<Items> getRestaurantItems(@PathVariable Long restaurantId) {
        return itemsService.findItemsByRestaurantId(restaurantId);
    }

    @PostMapping(path = "/restaurants/{restaurantId}/items", produces = {"application/json"}, consumes = {MediaType.APPLICATION_JSON_VALUE,
                                                                                                             MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseMessage createItem(@RequestPart String newItem, @RequestPart MultipartFile multipartFile, @PathVariable Long restaurantId) throws IOException {
        return itemsService.createItem(newItem, restaurantId, multipartFile);
    }

    @PutMapping(path = "/restaurants/{restaurantId}/items/{itemId}", produces = {"application/json"})
    public ResponseMessage updateItem(@PathVariable Long restaurantId, @PathVariable Long itemId, @RequestBody Items newItem) {
        return itemsService.updateItem(restaurantId, itemId, newItem);
    }

    @DeleteMapping(path = "/restaurants/{restaurantId}/items/{itemId}", produces = {"application/json"})
    public ResponseMessage deleteItem(@PathVariable Long restaurantId, @PathVariable Long itemId) {
        return itemsService.deleteItem(restaurantId, itemId);
    }

}
