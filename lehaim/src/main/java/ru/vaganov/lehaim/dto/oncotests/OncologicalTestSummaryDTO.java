package ru.vaganov.lehaim.dto.oncotests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.lehaim.dictionary.ChartType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "OncologicalTestDTO: Общая информация об обследовании с графиками")
public class OncologicalTestSummaryDTO {

    @Schema(title = "Индентификатор")
    private Long id;

    @Schema(title = "Дата обследования")
    private LocalDate testDate;

    @Schema(title = "Возможные графики",
            description = "Список графиков, которые возможно отобразить по текущему обследованию")
    List<ChartType> possibleCharts;
}
