package ru.vaganov.lehaim.oncotest.dto;

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

    @Schema(description = "Заметки к анализу")
    private String testNote;

    @Schema(description = "Анализ сдан во время прохождения курса лучевой терапии")
    private Boolean isDuringRadiationTherapy;
}
