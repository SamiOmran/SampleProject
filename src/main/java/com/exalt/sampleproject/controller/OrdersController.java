package com.exalt.sampleproject.controller;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.service.OrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping(path = "/restaurants/orders")
    public List<Orders> getAllOrders() {
        Orders orders = new Orders(5,"sami");
        ordersService.save(orders);
        return ordersService.findAll();
    }

    @PostMapping(path = "/restaurants/orders")
    public ResponseMessage createOrder(@RequestBody Orders newOrders) {

        return ordersService.createOrder(newOrders);
    }
}
