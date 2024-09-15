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
@Schema(title = "OncologicalTestDTO: Общая информация об обследовании")
public class OncologicalTestDTO {

    @Schema(title = "Индентификатор")
    private Long id;

    @Schema(title = "Дата обследования")
    private LocalDate testDate;

    @Schema(title = "Возможные графики",
            description = "Список графиков, которые возможно отобразить по текущему обследованию")
    List<ChartType> possibleCharts;
}
