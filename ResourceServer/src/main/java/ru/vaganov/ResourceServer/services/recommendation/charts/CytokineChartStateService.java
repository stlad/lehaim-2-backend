package ru.vaganov.ResourceServer.services.recommendation.charts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dictionary.recommendation.CytokineParameterRange;
import ru.vaganov.ResourceServer.exceptions.ChartStateException;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.CytokineChartState;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.repositories.CatalogRepository;
import ru.vaganov.ResourceServer.repositories.CytokineChartStateRepository;
import ru.vaganov.ResourceServer.repositories.RecommendationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CytokineChartStateService implements ChartStateService {
    private final CytokineChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;
    private final CatalogRepository catalogRepository;

    @Override
    public ChartType getChart() {
        return ChartType.CYTOKINE_PAIRS;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        CytokineChartState state = getState(patient, results);
        return state.getRecommendation() != null ?
                state.getRecommendation() :
                new Recommendation();
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
                getValue(CytokineChartState.Axis.TNFa, results, validationErrors));
        CytokineParameterRange ifnyRange = CytokineParameterRange.of(
                getValue(CytokineChartState.Axis.IFNy, results, validationErrors));
        CytokineParameterRange il2Range = CytokineParameterRange.of(
                getValue(CytokineChartState.Axis.IL2, results, validationErrors));

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

    private Double getValue(CytokineChartState.Axis axis, List<ParameterResult> results,
                            List<String> validationErrors) {
        Double firstParam = getParamResult(axis.getFirstParamId(), results, validationErrors);
        Double secondParam = getParamResult(axis.getSecondParamId(), results, validationErrors);
        if (firstParam == null || secondParam == null || secondParam == 0) {
            if (secondParam != null && secondParam == 0) {
                Parameter param = catalogRepository.findById(axis.getSecondParamId())
                        .orElseThrow(() -> new ParameterNotFoundException(axis.getSecondParamId()));
                validationErrors.add("Значение параметра " + param.toString() + " не должно быть 0");
            }
            return 0d;
        }
        return firstParam / secondParam;
    }

    private Double getParamResult(Long parameterId, List<ParameterResult> results,
                                  List<String> validationErrors) {
        Optional<ParameterResult> resOpt = results.stream()
                .filter(res -> res.getParameter().getId().equals(parameterId)).findAny();

        if (resOpt.isPresent()) {
            return resOpt.get().getValue();
        }
        Parameter param = catalogRepository.findById(parameterId)
                .orElseThrow(() -> new ParameterNotFoundException(parameterId));
        validationErrors.add("Обследование не содержит результата для параметра: " + param.toString());
        return null;
    }

    private void validateState(List<String> validationErrors) {
        if (!validationErrors.isEmpty())
            throw new ChartStateException(
                    validationErrors.stream().collect(Collectors.joining(";\n", "", "."))
            );
    }
}

