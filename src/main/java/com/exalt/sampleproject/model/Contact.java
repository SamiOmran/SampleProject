package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Contact {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @Column @JsonProperty @NonNull
    private String telephone;

    @Column @JsonProperty @NonNull
    private String phone;

    @ManyToOne @NonNull
    private Restaurants restaurants;

}
