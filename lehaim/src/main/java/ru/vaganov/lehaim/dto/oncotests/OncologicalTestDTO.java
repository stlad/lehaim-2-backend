package ru.vaganov.lehaim.dto.oncotests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.vaganov.lehaim.dictionary.ChartType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Общая информация об обследовании")
public class OncologicalTestDTO {

    @Schema(description = "Индентификатор")
    private Long id;

    @Schema(description = "Дата обследования", example = "2020-01-01")
    private LocalDate testDate;

    @Schema(description = "Список графиков, которые возможно отобразить по текущему обследованию")
    List<ChartType> possibleCharts;
}
