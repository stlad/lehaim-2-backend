package ru.vaganov.ResourceServer.models.dto;

import lombok.*;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class ParameterResultDTO {
    private Long id;
    private Double value;
    private ParameterDTO parameter;
    private OncologicalTestDTO attachedTest;
}
