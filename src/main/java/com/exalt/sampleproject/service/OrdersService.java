package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.repository.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Orders> findById(Long orderId) {
        return ordersRepo.findById(orderId);
    }

    public ResponseMessage save(Orders order) {
        ordersRepo.save(order);
        responseMessage.setMessage("Successfully order saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    /**
     * steps:
     * 1. get itemId and find it using ItemsService.
     * a. if found save the item inside Order.
     * b. if not found return responseMessage("could not save order").
     *
     * @param newOrder the order
     * @param itemId   for the item in the order
     * @return response message
     */
    public ResponseMessage createOrder(Orders newOrder, Long itemId) {
        Optional<Items> optionalItems = itemsService.findById(itemId);

        if (optionalItems.isPresent()) {
            Items item = optionalItems.get();
            int price = item.getPrice();
            newOrder.setTotal(newOrder.getQuantity() * price);

            newOrder.addItem(item);
            return save(newOrder);
        } else {
            responseMessage.setMessage("Could not find the item for the order");
            responseMessage.setStatus(-1);
            return responseMessage;
        }
    }

    public ResponseMessage updateOrder(Orders updatedOrder, Long orderId) {
        Optional<Orders> optionalOrder = findById(orderId);

        if (optionalOrder.isPresent()) {
            optionalOrder.map(order -> {
                int price = order.getTotal() / order.getQuantity();
                order.setQuantity(updatedOrder.getQuantity());
                order.setName(updatedOrder.getName());
                order.setItems(updatedOrder.getItems());
                order.setTotal(updatedOrder.getQuantity() * price);
                save(order);
                return order;
            });

            responseMessage.setMessage("Success updating order");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Could not found order");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteOrder(Long orderId) {
        Optional<Orders> optionalOrder = findById(orderId);

        if (optionalOrder.isPresent()) {
            ordersRepo.delete(optionalOrder.get());
            responseMessage.setMessage("Success deleting order");
            responseMessage.setStatus(1);
        } else {
            responseMessage.setMessage("Could not found order");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

}
