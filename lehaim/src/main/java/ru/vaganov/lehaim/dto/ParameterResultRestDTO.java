package ru.vaganov.lehaim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ParameterResultRestDTO {

    private Long catalogId;
    private Double value;
}
