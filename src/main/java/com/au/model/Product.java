package com.au.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("price")
    private double price;

    @JsonProperty("location")
    private String location;

    @JsonProperty("qty")
    private int qty;
}
