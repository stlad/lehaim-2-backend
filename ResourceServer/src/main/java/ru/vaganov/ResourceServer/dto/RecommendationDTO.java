package ru.vaganov.ResourceServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.ResourceServer.dictionary.ChartType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDTO {

    private UUID id;
    private String name;
    private String conclusion;
    private String recommendation;
    private ChartType chartType;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
