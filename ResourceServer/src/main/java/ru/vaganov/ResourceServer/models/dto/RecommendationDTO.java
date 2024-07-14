package ru.vaganov.ResourceServer.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDTO {

    private Long id;
    private Double rangeMin;
    private Double rangeMax;

    private String name;
    private String conclusion;
    private String recommendation;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    private Long firstParamId;
    private Long secondParamId;
}
