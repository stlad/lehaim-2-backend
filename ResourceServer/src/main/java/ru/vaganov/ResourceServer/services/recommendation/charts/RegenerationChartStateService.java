package ru.vaganov.ResourceServer.services.recommendation.charts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dictionary.recommendation.RegenerationParameterRanges;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.models.recommendations.RegenerationChartState;
import ru.vaganov.ResourceServer.repositories.CatalogRepository;
import ru.vaganov.ResourceServer.repositories.recommendation.RecommendationRepository;
import ru.vaganov.ResourceServer.repositories.recommendation.RegenerationChartStateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RegenerationChartStateService extends ChartStateService {
    private final RegenerationChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

    public RegenerationChartStateService(RegenerationChartStateRepository stateRepository,
                                         RecommendationRepository recommendationRepository,
                                         CatalogRepository catalogRepository) {
        super(catalogRepository);
        this.stateRepository = stateRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public ChartType getChart() {
        return ChartType.REGENERATION;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        RegenerationChartState state = getState(patient, results);
        return state.getRecommendation() != null ?
                state.getRecommendation() :
                new Recommendation();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient, List<ParameterResult> results) {
        RegenerationChartState state = getState(patient, results);
        recommendation = recommendationRepository.save(recommendation);
        state.setRecommendation(recommendation);
        stateRepository.save(state);
        return recommendation;
    }

    private RegenerationChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();
        Diagnosis diagnosis = patient.getDiagnosis();
        if (diagnosis == null) {
            validationErrors.add("У пациента не указан диагноз");
        }

        var neuMonRange = RegenerationParameterRanges.NEU_MON.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.NeuMon, results, validationErrors));
        var neuLymfRange = RegenerationParameterRanges.NEU_LYMF.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.NeuLymf, results, validationErrors));
        var lymfMonRange = RegenerationParameterRanges.LYMF_MON.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.LymfMon, results, validationErrors));

        validateState(validationErrors);

        Optional<RegenerationChartState> stateOpt = stateRepository.findState(
                diagnosis, neuLymfRange, neuMonRange, lymfMonRange);
        if (stateOpt.isPresent()) {
            return stateOpt.get();
        }
        RegenerationChartState state = RegenerationChartState.builder()
                .diagnosis(diagnosis)
                .rangeLymfMon(lymfMonRange)
                .rangeNeuLymf(neuLymfRange)
                .rangeNeuMon(neuMonRange)
                .build();
        return stateRepository.save(state);
    }
}
