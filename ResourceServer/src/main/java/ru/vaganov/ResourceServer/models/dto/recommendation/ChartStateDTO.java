package ru.vaganov.ResourceServer.models.dto.recommendation;

import lombok.Data;
import ru.vaganov.ResourceServer.dictionary.ChartType;

@Data
public class ChartStateDTO {
    private ChartType chartType;
    private Long diagnosisId;

}
