package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Items;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JsonItems {
    @JsonProperty private String message;
    @JsonProperty private List<Items> itemsList;

    public JsonItems(String message, List<Items> itemsList) {
        this.message = message;
        this.itemsList = itemsList;
    }
}
