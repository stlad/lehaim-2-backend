package ru.vaganov.ResourceServer.dto.recommendation;

import lombok.Data;
import ru.vaganov.ResourceServer.dictionary.ChartType;

@Data
public class ChartStateDTO {
    private ChartType chartType;
    private Long diagnosisId;

}
