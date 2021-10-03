package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Offers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JsonOffers {
    @JsonProperty private String message;
    @JsonProperty private List<Offers> offersList;

    public JsonOffers(String message, List<Offers> offersList) {
        this.message = message;
        this.offersList = offersList;
    }
}
