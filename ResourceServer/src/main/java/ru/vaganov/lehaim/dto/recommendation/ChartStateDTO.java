package ru.vaganov.lehaim.dto.recommendation;

import lombok.Data;
import ru.vaganov.lehaim.dictionary.ChartType;

@Data
public class ChartStateDTO {
    private ChartType chartType;
    private Long diagnosisId;

}
