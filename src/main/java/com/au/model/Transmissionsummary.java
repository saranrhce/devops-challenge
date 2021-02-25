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
public class Transmissionsummary {

    @JsonProperty("id")
    private String id;

    @JsonProperty("recordcount")
    private int recordcount;

    @JsonProperty("qtysum")
    private int qtysum;
}
