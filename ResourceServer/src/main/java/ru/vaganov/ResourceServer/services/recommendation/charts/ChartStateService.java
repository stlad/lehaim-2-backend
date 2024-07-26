package ru.vaganov.ResourceServer.services.recommendation.charts;

import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;

import java.util.List;

public interface ChartStateService {
    ChartType getChart();

    void process(Patient patient, List<ParameterResult> results);
}
