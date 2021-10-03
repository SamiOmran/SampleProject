package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Locations;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JsonLocations {
    @JsonProperty private String message;
    @JsonProperty private List<Locations> locationsList;

    public JsonLocations(String message, List<Locations> locationsList) {
        this.message = message;
        this.locationsList = locationsList;
    }
}
