package ru.vaganov.lehaim.dto.recommendation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.vaganov.lehaim.dictionary.ChartType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RecommendationDTO {
    private UUID id;

    private String name;
    private String conclusion;
    private String recommendation;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @NotNull(message = "chartType must not be null")
    private ChartType chartType;
}
