package ru.vaganov.lehaim.dto;

import lombok.*;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class ParameterResultDTO {
    private Long id;
    private Double value;
    private ParameterDTO parameter;
}
