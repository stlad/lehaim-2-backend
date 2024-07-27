package ru.vaganov.ResourceServer.services.recommendation.charts;

import jakarta.persistence.EntityNotFoundException;
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

import java.util.List;

@RequiredArgsConstructor
@Component
public class CytokineChartStateService implements ChartStateService {
    private CytokineChartStateRepository stateRepository;

    @Override
    public ChartType getChart() {
        return ChartType.CYTOKINE_PAIRS;
    }

    @Override
    public Recommendation getRecommendation(Patient patient, List<ParameterResult> results) {
        Diagnosis diagnosis = patient.getDiagnosis();
        return null;
    }

    private CytokineChartState getState(Patient patient, List<ParameterResult> results) {
        CytokineParameterRange tnfRange = CytokineParameterRange.of(getValue(CytokineChartState.Axis.TNFa, results));
        CytokineParameterRange ifnyRange = CytokineParameterRange.of(getValue(CytokineChartState.Axis.IL2, results));
        CytokineParameterRange il2Range = CytokineParameterRange.of(getValue(CytokineChartState.Axis.IL2, results));
        Diagnosis diagnosis = patient.getDiagnosis();

        return stateRepository.findState(diagnosis, tnfRange,ifnyRange,il2Range)
                .orElseThrow(()->new EntityNotFoundException("Cannot find state"));
    }

    private Double getValue(CytokineChartState.Axis axis, List<ParameterResult> results) {
        Double firstParam = results.stream().filter(res -> res.getParameter().getId().equals(axis.getFirstParamId()))
                .findAny().orElseThrow(() -> new ParameterNotFoundException(axis.getFirstParamId())).getValue();
        Double secondPAram = results.stream().filter(res -> res.getParameter().getId().equals(axis.getSecondParamId()))
                .findAny().orElseThrow(() -> new ParameterNotFoundException(axis.getSecondParamId())).getValue();
        return firstParam / secondPAram;
    }
}

