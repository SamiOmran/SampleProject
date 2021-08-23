package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.service.ItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemsController {
    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping(path = "/restaurants/{restaurantId}/items", produces = {"application/json"})
    public List<Items> getItems(@PathVariable Long restaurantId) {
        return itemsService.findItemsByRestaurantId(restaurantId);
    }

    @PostMapping(path = "/restaurants/{restaurantId}/items", produces = {"application/json"})
    public ResponseMessage createItem(@RequestBody Items newItem, @PathVariable Long restaurantId) {
        return itemsService.createItem(newItem, restaurantId);
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
