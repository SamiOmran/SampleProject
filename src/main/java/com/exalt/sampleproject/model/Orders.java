package com.exalt.sampleproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @JsonProperty
    @GeneratedValue
    private Long id;

    @NonNull @JsonProperty
    private int quantity;

    @JsonProperty
    private int total;

    @NonNull @JsonProperty
    private String name;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
    )
    private Set<Items> items;
}
