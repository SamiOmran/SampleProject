package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.repository.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepo ordersRepo;
    private final ItemsService itemsService;
    private final ResponseMessage responseMessage = new ResponseMessage();

    public OrdersService(OrdersRepo ordersRepo, ItemsService itemsService) {
        this.ordersRepo = ordersRepo;
        this.itemsService = itemsService;
    }

    public List<Orders> findAll() {
        return ordersRepo.findAll();
    }

    public ResponseMessage save(Orders orders) {
        ordersRepo.save(orders);
        responseMessage.setMessage("Success adding new order");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public ResponseMessage createOrder(Orders newOrders) {
        return save(newOrders);
    }
}
