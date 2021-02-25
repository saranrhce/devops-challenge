package com.au.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @JsonProperty("products")
    List<Product> products;

    @JsonProperty("transmissionsummary")
    private Transmissionsummary transmissionsummary;
}
