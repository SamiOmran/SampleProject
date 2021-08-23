package com.exalt.sampleproject.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AllData {
    private String name;
    private List<Locations> locations;
    private List<Contacts> contacts;

    public AllData(String name, List<Locations> locations, List<Contacts> contacts) {
        this.name = name;
        this.locations = locations;
        this.contacts = contacts;
    }
}
