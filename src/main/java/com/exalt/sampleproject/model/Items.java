package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Items {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @NonNull @JsonProperty
    private String description;

    @NonNull @JsonProperty
    private int price;

    @Lob private Byte[] image;

    @ManyToOne @JoinColumn(name = "restaurant_id")
    @NonNull @JsonProperty
    private Restaurants restaurants;

}
