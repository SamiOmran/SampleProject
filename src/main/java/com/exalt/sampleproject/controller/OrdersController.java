package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.exception.ResourceNotFoundException;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping(path = "/restaurants/orders")
    public List<Orders> getAllOrders() {
        return ordersService.findAll();
    }

    @GetMapping(path = "/restaurants/orders/{orderId}")
    public Orders getOrder(@PathVariable Long orderId) {
        Optional<Orders> optionalOrders = ordersService.findById(orderId);

        if (optionalOrders.isPresent()) {
            return optionalOrders.get();
        } else {
            throw new ResourceNotFoundException("Order you asked not found");
        }
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
