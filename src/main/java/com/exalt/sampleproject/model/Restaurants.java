package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

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

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    private List<Location> locations = new ArrayList<>();

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    private List<Contact> contacts;
}
