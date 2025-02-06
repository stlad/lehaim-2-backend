package ru.vaganov.lehaim.recommendation.charts;

import lombok.Data;
import ru.vaganov.lehaim.dictionary.ChartType;

@Data
public class ChartStateDTO {
    private ChartType chartType;
    private Long diagnosisId;

}
