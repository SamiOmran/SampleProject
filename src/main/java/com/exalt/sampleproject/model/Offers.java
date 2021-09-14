package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Offers {

    @Id @JsonProperty
    @GeneratedValue
    private Long id;

    @NonNull @JsonProperty
    private LocalDate localDate = LocalDate.now();

    @NonNull @JsonProperty
    private String offer;

    @ManyToOne @JoinColumn(name = "restaurant_id")
    @JsonProperty
    private Restaurants restaurants;
}
