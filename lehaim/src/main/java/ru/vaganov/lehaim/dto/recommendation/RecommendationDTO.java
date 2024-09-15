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

    @Schema(description = "Наименование")
    private String name;

    @Schema(description = "Заключение")
    private String conclusion;

    @Schema(description = "Рекомендация")
    private String recommendation;

    @Schema(description = "Дата и время создания")
    private LocalDateTime dateCreated;

    @Schema(description = "Дата и время обновления")
    private LocalDateTime dateUpdated;

    @Schema(description = "Сообщение об ошибке. Пусто, если ошибок нет")
    private String errorMessage;

    @NotNull(message = "chartType must not be null")
    private ChartType chartType;
}
