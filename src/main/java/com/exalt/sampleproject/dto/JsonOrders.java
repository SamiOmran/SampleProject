package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JsonOrders {
    @JsonProperty private String message;
    @JsonProperty private List<Orders> ordersList;

    public JsonOrders(String message, List<Orders> ordersList) {
        this.message = message;
        this.ordersList = ordersList;
    }
}
