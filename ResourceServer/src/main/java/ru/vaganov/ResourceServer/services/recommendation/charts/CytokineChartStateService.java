package ru.vaganov.ResourceServer.services.recommendation.charts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dictionary.recommendation.CytokineParameterRange;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.CytokineChartState;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.repositories.CytokineChartStateRepository;
import ru.vaganov.ResourceServer.repositories.RecommendationRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CytokineChartStateService implements ChartStateService {
    private final CytokineChartStateRepository stateRepository;
    private final RecommendationRepository recommendationRepository;

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
        CytokineParameterRange tnfRange = CytokineParameterRange.of(getValue(CytokineChartState.Axis.TNFa, results));
        CytokineParameterRange ifnyRange = CytokineParameterRange.of(getValue(CytokineChartState.Axis.IFNy, results));
        CytokineParameterRange il2Range = CytokineParameterRange.of(getValue(CytokineChartState.Axis.IL2, results));
        Diagnosis diagnosis = patient.getDiagnosis();

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

    private Double getValue(CytokineChartState.Axis axis, List<ParameterResult> results) {
        Double firstParam = results.stream().filter(res -> res.getParameter().getId().equals(axis.getFirstParamId()))
                .findAny().orElseThrow(() -> new ParameterNotFoundException(axis.getFirstParamId())).getValue();
        Double secondPAram = results.stream().filter(res -> res.getParameter().getId().equals(axis.getSecondParamId()))
                .findAny().orElseThrow(() -> new ParameterNotFoundException(axis.getSecondParamId())).getValue();
        return firstParam / secondPAram;
    }
}

