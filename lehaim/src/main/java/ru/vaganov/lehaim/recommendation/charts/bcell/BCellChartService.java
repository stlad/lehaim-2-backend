package ru.vaganov.lehaim.recommendation.charts.bcell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BCellChartService extends ChartStateService {
    private final BChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

    public BCellChartService(ParameterCatalogRepository parameterCatalogRepository, BChartStateRepository stateRepository,
                             RecommendationRepository recommendationRepository) {
        super(parameterCatalogRepository);
        this.stateRepository = stateRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public ChartType getChart() {
        return ChartType.B_CELL;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        BChartState state = getState(patient, results);
        return state.getRecommendation();
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient, List<ParameterResult> results) {
        log.info("Получение рекомендации для пациента {}", patient.getId());
        BChartState state = getState(patient, results);
        recommendation = recommendationRepository.save(recommendation);
        state.setRecommendation(recommendation);
        stateRepository.save(state);
        return recommendation;
    }

    private BChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();
        var stateBuilder = BChartState.builder();
        stateBuilder.diagnosis(patient.getDiagnosis());

        var igmValue = getParamResult(MostUsedParameters.IgM.getId(), results, validationErrors);
        if (igmValue != null) {
            if (igmValue >= 5) {
                stateBuilder.rangeCd19(BParameterRanges.CD19.EMPTY);
            } else if (igmValue > 0.45) {
                stateBuilder.rangeCd19(getCd19(results, validationErrors));
            } else {
                stateBuilder.rangeCd19Tnk(getCd19Tnk(results, validationErrors));
                stateBuilder.rangeIga(getIga(results, validationErrors));
                stateBuilder.rangeIgg(getIgg(results, validationErrors));
            }
        }

        stateBuilder.rangeNeuLymf(getNeuLymf(results, validationErrors));
        stateBuilder.rangeNeuCd19(getNeuCd19(results, validationErrors));
        stateBuilder.rangeNeuCd4(getNeuCd4(results, validationErrors));
        stateBuilder.rangeCd19Cd4(getCd19Cd4(results, validationErrors));

        validateState(validationErrors);

        var state = stateBuilder.build();
        Optional<BChartState> stateOpt = stateRepository.findState(state.getDiagnosis(), state);

        return stateOpt.orElseGet(() -> stateRepository.save(state));
    }

    private BParameterRanges.CD19 getCd19(List<ParameterResult> results,
                                          List<String> validationErrors) {
        return BParameterRanges.CD19.of(
                getParamResult(MostUsedParameters.CD19.getId(), results, validationErrors)
        );
    }

    private BParameterRanges.CD19_TNK getCd19Tnk(List<ParameterResult> results,
                                                 List<String> validationErrors) {
        return BParameterRanges.CD19_TNK.of(
                getParamResult(MostUsedParameters.CD19.getId(), results, validationErrors)
                        + getParamResult(MostUsedParameters.TNK_CELLS.getId(), results, validationErrors)
        );
    }

    private BParameterRanges.IgG getIgg(List<ParameterResult> results,
                                        List<String> validationErrors) {
        return BParameterRanges.IgG.of(
                getParamResult(MostUsedParameters.IgG.getId(), results, validationErrors)
        );
    }

    private BParameterRanges.IgA getIga(List<ParameterResult> results,
                                        List<String> validationErrors) {
        return BParameterRanges.IgA.of(
                getParamResult(MostUsedParameters.IgA.getId(), results, validationErrors)
        );
    }

    private BParameterRanges.NEU_LYMF getNeuLymf(List<ParameterResult> results,
                                                 List<String> validationErrors) {
        return BParameterRanges.NEU_LYMF.of(
                getDivisionOfParameters(MostUsedParameters.NEU.getId(), MostUsedParameters.LYMF.getId(),
                        results, validationErrors));
    }

    private BParameterRanges.NEU_CD19 getNeuCd19(List<ParameterResult> results,
                                                 List<String> validationErrors) {
        return BParameterRanges.NEU_CD19.of(
                getDivisionOfParameters(MostUsedParameters.NEU.getId(), MostUsedParameters.CD19.getId(),
                        results, validationErrors));
    }

    private BParameterRanges.NEU_CD4 getNeuCd4(List<ParameterResult> results,
                                               List<String> validationErrors) {
        return BParameterRanges.NEU_CD4.of(
                getDivisionOfParameters(MostUsedParameters.NEU.getId(), MostUsedParameters.CD4.getId(),
                        results, validationErrors));
    }

    private BParameterRanges.CD19_CD4 getCd19Cd4(List<ParameterResult> results,
                                                 List<String> validationErrors) {
        return BParameterRanges.CD19_CD4.of(
                getDivisionOfParameters(MostUsedParameters.CD19.getId(), MostUsedParameters.CD4.getId(),
                        results, validationErrors));
    }
}
