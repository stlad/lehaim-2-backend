package ru.vaganov.lehaim.recommendation.charts.cytokine;

import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CytokineChartStateService extends ChartStateService {
    private final CytokineChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

    public CytokineChartStateService(CytokineChartStateRepository stateRepository,
                                     RecommendationRepository recommendationRepository,
                                     ParameterCatalogRepository parameterCatalogRepository) {
        super(parameterCatalogRepository);
        this.stateRepository = stateRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public ChartType getChart() {
        return ChartType.CYTOKINE_PAIRS;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        CytokineChartState state = getState(patient, results);
        return state.getRecommendation();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient,
                                             List<ParameterResult> results) {
        CytokineChartState state = getState(patient, results);
        recommendation = recommendationRepository.save(recommendation);
        state.setRecommendation(recommendation);
        stateRepository.save(state);
        return recommendation;
    }

    private CytokineChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();
        Diagnosis diagnosis = patient.getDiagnosis();
        if (diagnosis == null) {
            validationErrors.add("У пациента не указан диагноз");
        }

        CytokineParameterRange tnfRange = CytokineParameterRange.of(
                getDivisionForChartAxis(CytokineChartState.Axis.TNFa, results, validationErrors));
        CytokineParameterRange ifnyRange = CytokineParameterRange.of(
                getDivisionForChartAxis(CytokineChartState.Axis.IFNy, results, validationErrors));
        CytokineParameterRange il2Range = CytokineParameterRange.of(
                getDivisionForChartAxis(CytokineChartState.Axis.IL2, results, validationErrors));

        validateState(validationErrors);

        Optional<CytokineChartState> stateOpt = stateRepository.findState(diagnosis, tnfRange, ifnyRange, il2Range);
        if (stateOpt.isPresent()) {
            return stateOpt.get();
        }
        CytokineChartState state = CytokineChartState.builder()
                .diagnosis(diagnosis)
                .rangeIL2(il2Range)
                .rangeIFNy(ifnyRange)
                .rangeTNFa(tnfRange)
                .build();
        return stateRepository.save(state);
    }
}
