package ru.vaganov.ResourceServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.ResourceServer.models.ParameterResult;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ParameterResultRestDTO {

    private Long catalogId;
    private Double value;
}
