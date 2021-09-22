package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Contacts;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
public class JsonContacts {

    @JsonProperty private String message;
    @JsonProperty private List<Contacts> contactsList;
}
