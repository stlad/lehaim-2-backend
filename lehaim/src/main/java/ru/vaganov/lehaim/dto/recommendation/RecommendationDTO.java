package ru.vaganov.lehaim.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "Сообщение об ошибке", description = "Пусто, если ошибок нет")
    private String errorMessage;

    @NotNull(message = "chartType must not be null")
    private ChartType chartType;
}
