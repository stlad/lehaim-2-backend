package ru.vaganov.lehaim.dto.oncotests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Schema(
        description = "Полная информация об обслоедовании, включает в себя результаты анализов"
)
public class OncologicalTestRestDTO {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Дата обследования", example = "2020-01-01")
    private LocalDate testDate;

    @NotNull(message = "results must not be null")
    private List<ParameterResultRestDTO> results;

    @Schema(description = "Список графиков, которые возможно отобразить по текущему обследованию")
    List<ChartType> possibleCharts;
}

