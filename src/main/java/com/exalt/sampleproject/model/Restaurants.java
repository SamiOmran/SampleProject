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

    @OneToMany(mappedBy = "restaurants")
    private List<Location> locations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    public void addLocation(Location location) {
        this.locations.add(location);
    }


}
