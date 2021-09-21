package com.exalt.sampleproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ResponseMessage {
    @JsonProperty private String message;
    @JsonProperty private int status;

    public ResponseMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
