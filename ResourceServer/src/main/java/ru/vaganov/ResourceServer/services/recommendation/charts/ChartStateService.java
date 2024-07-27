package ru.vaganov.ResourceServer.services.recommendation.charts;

import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;

import java.util.List;

public interface ChartStateService {
    ChartType getChart();

    /**
     * Получить рекомендацию на основе входных параметров обследования
     *
     * @param patient Пациент
     * @param results Список парметров из обследования
     * @return рекомендация, соответсвующая состоянию на основе входных параметров
     */
    Recommendation getRecommendation(Patient patient, List<ParameterResult> results);

    //TODO saveRecommendation(), editRecommendation()
}
