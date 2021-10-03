package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.JsonItems;
import com.exalt.sampleproject.dto.JsonOrders;
import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.Items;
import com.exalt.sampleproject.model.Orders;
import com.exalt.sampleproject.repository.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepo ordersRepo;
    private final ItemsService itemsService;
    private final ResponseMessage responseMessage = new ResponseMessage();
    private final static String SUCCESS_MESSAGE = "Success getting orders";
    private final static String FAIL_MESSAGE = "No orders Available";

    public OrdersService(OrdersRepo ordersRepo, ItemsService itemsService) {
        this.ordersRepo = ordersRepo;
        this.itemsService = itemsService;
    }

    public JsonOrders findAll() {
        List<Orders> ordersList = ordersRepo.findAll();
        String message = (ordersList.isEmpty())? FAIL_MESSAGE : SUCCESS_MESSAGE;
        return new JsonOrders(message, ordersList);
    }

    public JsonOrders findById(Long orderId) {
        Optional<Orders> optionalOrders = ordersRepo.findById(orderId);
        JsonOrders jsonOrders;
        if (optionalOrders.isPresent()) {
            List<Orders> ordersList = new ArrayList<>();
            ordersList.add(optionalOrders.get());
            jsonOrders = new JsonOrders(SUCCESS_MESSAGE, ordersList);
        } else {
            jsonOrders = new JsonOrders(SUCCESS_MESSAGE, null);
        }
        return jsonOrders;
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
        JsonItems jsonItems = itemsService.findById(itemId);
        Optional<Items> optionalItems = Optional.of(jsonItems.getItemsList().get(0));

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
        JsonOrders jsonOrders = findById(orderId);
        Optional<Orders> optionalOrder = Optional.of(jsonOrders.getOrdersList().get(0));

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
        JsonOrders jsonOrders = findById(orderId);
        Optional<Orders> optionalOrder = Optional.of(jsonOrders.getOrdersList().get(0));

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
