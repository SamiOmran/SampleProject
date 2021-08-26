package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Contacts {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @Column @JsonProperty @NonNull
    private String type;

    @Column @JsonProperty @NonNull
    private String value;

    @ManyToOne(cascade = CascadeType.ALL) @NonNull
    @JoinColumn(name = "location_id")
    private Locations location;

}
