package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @Column @JsonProperty @NonNull
    private String city;

    @Column @JsonProperty @NonNull
    private String street;

    @Column @JsonProperty @NonNull
    private String section;

    @ManyToOne @NonNull
    private Restaurants restaurants;
}
