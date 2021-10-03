package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.JsonOrders;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.service.OrdersService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping(path = "/restaurants/orders")
    public JsonOrders getAllOrders() {
        return ordersService.findAll();
    }

    @GetMapping(path = "/restaurants/orders/{orderId}")
    public JsonOrders getOrder(@PathVariable Long orderId) {
        return ordersService.findById(orderId);
    }

    @PostMapping(path = "/restaurants/orders/{itemId}")
    public ResponseMessage createOrder(@RequestBody Orders newOrders, @PathVariable Long itemId) {
        return ordersService.createOrder(newOrders, itemId);
    }

    @PutMapping(path = "/restaurants/orders/{orderId}")
    public ResponseMessage updateOrder(@RequestBody Orders order, @PathVariable Long orderId) {
        return ordersService.updateOrder(order, orderId);
    }

    @DeleteMapping("/restaurants/orders/{orderId}")
    public ResponseMessage deleteOrder(@PathVariable Long orderId) {
        return ordersService.deleteOrder(orderId);
    }
}
