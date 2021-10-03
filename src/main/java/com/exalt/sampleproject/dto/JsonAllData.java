package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.AllData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JsonAllData {
    @JsonProperty private String message;
    @JsonProperty private List<AllData> allDataList;

    public JsonAllData(String message, List<AllData> allDataList) {
        this.message = message;
        this.allDataList = allDataList;
    }

}
