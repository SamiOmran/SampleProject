package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Restaurants {
    @Id @GeneratedValue @JsonProperty
    private Long id;

    @Column(nullable = false) @JsonProperty @NonNull
    private String name;

    @JsonProperty
    @OneToMany(mappedBy = "restaurants")
    private List<Location> locations = new ArrayList<>();

    @OneToMany(mappedBy = "restaurants")
    private List<Contact> contacts = new ArrayList<>();

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

}
