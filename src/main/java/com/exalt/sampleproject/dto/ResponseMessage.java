package com.exalt.sampleproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseMessage {
    @JsonProperty private String message;
    @JsonProperty private int status;
}
