package ru.vaganov.lehaim.recommendation.charts.regeneration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.repositories.CatalogRepository;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;

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
        return state.getRecommendation();
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

        var neuMonRange = getNeuMonRange(results, validationErrors);
        var neuLymfRange = getNeuLymfRange(results, validationErrors);
        var lymfMonRange = getLymfMonRange(results, validationErrors);

        var neu = getNeutrophils(results, validationErrors);
        var leu = getLeukocytes(results, validationErrors);
        var hgb = getHemoblobin(results, patient, validationErrors);
        var plt = getPlatelets(results, validationErrors);

        validateState(validationErrors);

        Optional<RegenerationChartState> stateOpt = stateRepository.findState(
                diagnosis, neuLymfRange, neuMonRange, lymfMonRange, neu, leu, plt, hgb);
        if (stateOpt.isPresent()) {
            return stateOpt.get();
        }
        RegenerationChartState state = RegenerationChartState.builder()
                .diagnosis(diagnosis)
                .rangeLymfMon(lymfMonRange)
                .rangeNeuLymf(neuLymfRange)
                .rangeNeuMon(neuMonRange)
                .rangeHgb(hgb)
                .rangeLeu(leu)
                .rangeNeu(neu)
                .rangePlt(plt)
                .build();
        return stateRepository.save(state);
    }

    private RegenerationParameterRanges.LYMF_MON getLymfMonRange(List<ParameterResult> results, List<String> validationErrors) {
        return RegenerationParameterRanges.LYMF_MON.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.LymfMon, results, validationErrors));
    }

    private RegenerationParameterRanges.NEU_LYMF getNeuLymfRange(List<ParameterResult> results,
                                                                 List<String> validationErrors) {
        return RegenerationParameterRanges.NEU_LYMF.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.NeuLymf, results, validationErrors));
    }

    private RegenerationParameterRanges.NEU_MON getNeuMonRange(List<ParameterResult> results,
                                                               List<String> validationErrors) {
        return RegenerationParameterRanges.NEU_MON.of(
                getDivisionForChartAxis(RegenerationChartState.Axis.NeuMon, results, validationErrors));
    }

    private RegenerationParameterRanges.LEUKOCYTES getLeukocytes(List<ParameterResult> results,
                                                                 List<String> validationErrors) {
        Double result = getParamResult(RegenerationChartState.Axis.Leukocytes.getFirstParamId(), results, validationErrors);
        return result == null ? null : RegenerationParameterRanges.LEUKOCYTES.of(result);
    }

    private RegenerationParameterRanges.HEMOGLOBIN getHemoblobin(List<ParameterResult> results, Patient patient,
                                                                 List<String> validationErrors) {
        if (patient.getGender() == null) {
            validationErrors.add("У пациента не указан пол");
            return null;
        }
        Double result = getParamResult(RegenerationChartState.Axis.Hemoglobin.getFirstParamId(), results, validationErrors);
        return result == null ? null : RegenerationParameterRanges.HEMOGLOBIN.of(result, patient.getGender());
    }

    private RegenerationParameterRanges.NEUTROPHILS getNeutrophils(List<ParameterResult> results,
                                                                   List<String> validationErrors) {
        Double result = getParamResult(RegenerationChartState.Axis.Neutrophils.getFirstParamId(),results, validationErrors);
        return result == null ? null : RegenerationParameterRanges.NEUTROPHILS.of(result);
    }

    private RegenerationParameterRanges.PLATELETS getPlatelets(List<ParameterResult> results,
                                                               List<String> validationErrors) {
        Double result = getParamResult(RegenerationChartState.Axis.Platelets.getFirstParamId(),results, validationErrors);
        return result == null ? null : RegenerationParameterRanges.PLATELETS.of(result);
    }
}
