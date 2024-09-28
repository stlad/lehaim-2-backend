package ru.vaganov.lehaim.services.recommendation.charts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.dictionary.recommendation.SIParameterRanges;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.models.recommendations.Recommendation;
import ru.vaganov.lehaim.models.recommendations.SIChartState;
import ru.vaganov.lehaim.repositories.CatalogRepository;
import ru.vaganov.lehaim.repositories.recommendation.RecommendationRepository;
import ru.vaganov.lehaim.repositories.recommendation.SIChartStateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SIChartService extends ChartStateService {
    private final SIChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

    public SIChartService(SIChartStateRepository stateRepository,
                          RecommendationRepository recommendationRepository,
                          CatalogRepository catalogRepository) {
        super(catalogRepository);
        this.stateRepository = stateRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public ChartType getChart() {
        return ChartType.SYSTEMIC_INFLAMMATION;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        SIChartState state = getState(patient, results);
        return state.getRecommendation();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient, List<ParameterResult> results) {
        SIChartState state = getState(patient, results);
        recommendation = recommendationRepository.save(recommendation);
        state.setRecommendation(recommendation);
        stateRepository.save(state);
        return recommendation;
    }

    private SIChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();
        Diagnosis diagnosis = patient.getDiagnosis();
        if (diagnosis == null) {
            validationErrors.add("У пациента не указан диагноз");
        }

        Double neu = getParamResult(MostUsedParameters.NEU.getId(), results, validationErrors);
        Double lymf = getParamResult(MostUsedParameters.LYMF.getId(), results, validationErrors);
        Double mon = getParamResult(MostUsedParameters.MON.getId(), results, validationErrors);
        Double plt = getParamResult(MostUsedParameters.PLT.getId(), results, validationErrors);

        var siriRange = getSiriRange(neu, mon, lymf, validationErrors);
        var pivRange = getPivRange(neu, mon, lymf, plt, validationErrors);
        var neuDensityRange = getNeuDensityRange(neu, mon, lymf, validationErrors);

        validateState(validationErrors);

        Optional<SIChartState> stateOpt = stateRepository.findState(diagnosis, siriRange, pivRange, neuDensityRange);
        if (stateOpt.isPresent()) {
            return stateOpt.get();
        }
        SIChartState state = SIChartState.builder()
                .diagnosis(diagnosis)
                .rangeSiri(siriRange)
                .rangePiv(pivRange)
                .rangeNeuDensity(neuDensityRange)
                .build();
        return stateRepository.save(state);
    }

    private SIParameterRanges.Siri getSiriRange(Double neu, Double mon, Double lymf, List<String> validationErrors) {
        if (neu == null || mon == null || lymf == null) {
            return null;
        }
        if (lymf == 0) {
            validationErrors.add("Параметр LYMF равен 0");
            return null;
        }
        return SIParameterRanges.Siri.of((neu * mon) / lymf);
    }

    private SIParameterRanges.Piv getPivRange(Double neu, Double mon, Double lymf, Double plt, List<String> validationErrors) {
        if (neu == null || mon == null || lymf == null || plt == null) {
            return null;
        }
        if (lymf == 0) {
            validationErrors.add("Параметр LYMF равен 0");
            return null;
        }
        return SIParameterRanges.Piv.of((neu * mon * plt) / lymf);
    }

    private SIParameterRanges.NeuDensity getNeuDensityRange(Double neu, Double mon, Double lymf, List<String> validationErrors) {
        if (neu == null || mon == null || lymf == null) {
            return null;
        }
        if ((lymf + mon) == 0) {
            validationErrors.add("Параметр LYMF+MON равен 0");
            return null;
        }
        return SIParameterRanges.NeuDensity.of(neu / (lymf + mon));
    }
}
