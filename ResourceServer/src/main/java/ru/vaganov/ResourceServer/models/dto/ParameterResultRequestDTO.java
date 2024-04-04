package ru.vaganov.ResourceServer.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParameterResultRequestDTO {

    private Long catalogId;
    private Double value;
}
