package ru.vaganov.lehaim.recommendation.charts.bcell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.exceptions.NotImplementedException;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.repositories.CatalogRepository;

import java.util.List;

@Slf4j
@Component
public class BCellChartService extends ChartStateService {
    public BCellChartService(CatalogRepository catalogRepository) {
        super(catalogRepository);
    }

    @Override
    public ChartType getChart() {
        return ChartType.B_CELL;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        throw new NotImplementedException();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient, List<ParameterResult> results) {
        throw new NotImplementedException();
    }
}
