package com.exalt.sampleproject.dto;

import com.exalt.sampleproject.model.Contacts;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JsonContacts {

    @JsonProperty private String message;
    @JsonProperty private List<Contacts> contactsList;

    public JsonContacts(String message, List<Contacts> contactsList) {
        this.message = message;
        this.contactsList = contactsList;
    }
}
