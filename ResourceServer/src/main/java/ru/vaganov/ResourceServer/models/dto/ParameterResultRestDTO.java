package ru.vaganov.ResourceServer.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vaganov.ResourceServer.models.ParameterResult;

@Data
@AllArgsConstructor
public class ParameterResultRestDTO {

    private Long catalogId;
    private Double value;
}
