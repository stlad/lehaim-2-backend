package ru.vaganov.lehaim.recommendation.charts.tcell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.repositories.CatalogRepository;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TCellChartService extends ChartStateService {
    private final TChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

    public TCellChartService(CatalogRepository catalogRepository, TChartStateRepository stateRepository,
                             RecommendationRepository recommendationRepository) {
        super(catalogRepository);
        this.stateRepository = stateRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public ChartType getChart() {
        return ChartType.T_CELL;
    }

    @Override
    public Recommendation saveRecommendation(Recommendation recommendation, Patient patient, List<ParameterResult> results) {
        log.info("Получение рекомендации для пациента {}",patient.getId());
        TChartState state = getState(patient, results);
        recommendation = recommendationRepository.save(recommendation);
        state.setRecommendation(recommendation);
        stateRepository.save(state);
        return recommendation;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        TChartState state = getState(patient, results);
        return state.getRecommendation();
    }


    private TChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();

        var cd4ToCd8 = compareCd4ToCd8(results, validationErrors);
        var stateBuilder = TChartState.builder();
        stateBuilder.diagnosis(patient.getDiagnosis());
        stateBuilder.cd4compareCd8(cd4ToCd8);

        switch (cd4ToCd8) {
            case 1: {
                processCD4GreaterCD8(stateBuilder, results, validationErrors);
                break;
            }
            case 0: {
                processCD4EqualCD8(stateBuilder, results, validationErrors);
                break;
            }
            case -1: {
                processCD4LessCD8(stateBuilder, results, validationErrors);
                break;
            }
            default:
                throw new IllegalStateException("Не удалось обработать CD4 или CD8");
        }

        validateState(validationErrors);

        var state = stateBuilder.build();
        Optional<TChartState> stateOpt = stateRepository.findState(state.getDiagnosis(),
                state.getCd4compareCd8(), state.getRangeCd4(), state.getRangeNeuLymf());

        return stateOpt.orElseGet(() -> stateRepository.save(state));
    }

    private Integer compareCd4ToCd8(List<ParameterResult> results, List<String> validationErrors) {
        var cd4 = getParamResult(MostUsedParameters.CD4.getId(), results, validationErrors);
        var cd8 = getParamResult(MostUsedParameters.CD8.getId(), results, validationErrors);
        return cd4.compareTo(cd8);
    }

    /**
     * Если CD4/CD8>1, то необходимо анализировать значение показателей CD4 и NEU/LYMF
     */
    private void processCD4GreaterCD8(TChartState.TChartStateBuilder stateBuilder,
                                      List<ParameterResult> results, List<String> validationErrors) {
        stateBuilder.rangeCd4(getCd4Range(results, validationErrors));
        stateBuilder.rangeNeuLymf(getNeuLymfRange(results, validationErrors));
    }

    /**
     * Если CD4/CD8=1, то необходимо анализировать значение показателя CD4, CD8 и NEU/LYMF.
     * Важно, что значения CD4 и CD8 могут быть только в строго определенных диапазонах, иначе – ошибка
     * «Проверьте значения параметров CD4 и CD8»
     * CD4 и СD8 внутри [0,48;0,52]
     */
    private void processCD4EqualCD8(TChartState.TChartStateBuilder stateBuilder,
                                    List<ParameterResult> results, List<String> validationErrors) {
        var cd4 = getParamResult(MostUsedParameters.CD4.getId(), results, validationErrors);
        var cd8 = getParamResult(MostUsedParameters.CD8.getId(), results, validationErrors);
        if (cd4 < 0.48 || cd8 < 0.48 || cd4 > 0.52 || cd8 > 0.52) {
            validationErrors.add("Проверьте значения параметров CD4 и CD8");
            return;
        }
        stateBuilder.rangeCd4(getCd4Range(results, validationErrors));
        stateBuilder.rangeNeuLymf(getNeuLymfRange(results, validationErrors));
    }

    /**
     * Если CD4/CD8<1, то необходимо анализировать ТОЛЬКО NEU/LYMF.
     */
    private void processCD4LessCD8(TChartState.TChartStateBuilder stateBuilder,
                                   List<ParameterResult> results, List<String> validationErrors) {
        stateBuilder.rangeNeuLymf(getNeuLymfRange(results, validationErrors));
    }

    private TParameterRanges.NEU_LYMF getNeuLymfRange(List<ParameterResult> results,
                                                      List<String> validationErrors) {
        return TParameterRanges.NEU_LYMF.of(
                getDivisionOfParameters(MostUsedParameters.NEU.getId(), MostUsedParameters.LYMF.getId(),
                        results, validationErrors));
    }

    private TParameterRanges.CD4 getCd4Range(List<ParameterResult> results,
                                             List<String> validationErrors) {
        Double result = getParamResult(MostUsedParameters.CD4.getId(), results, validationErrors);
        return result == null ? null : TParameterRanges.CD4.of(result);
    }
}
