package ru.vaganov.lehaim.recommendation.charts.bcell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.exceptions.NotImplementedException;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.charts.ChartStateService;
import ru.vaganov.lehaim.recommendation.charts.tcell.TChartState;
import ru.vaganov.lehaim.repositories.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private TChartState getState(Patient patient, List<ParameterResult> results) {
        List<String> validationErrors = new ArrayList<>();
        var stateBuilder = BChartState.builder();
        stateBuilder.diagnosis(patient.getDiagnosis());

        var igmValue = getParamResult(MostUsedParameters.IgM.getId(), results, validationErrors);
        if (igmValue >= 5) {
            //b_lymf set none
            //iga set none
            //igg set none
        } else if (igmValue > 0.45) {
            //b_lymf диапазон
            //iga set none
            //igg set none
        } else {
            //b_lymf+tna диапазон
            //iga set диапазон
            //igg set диапазон
        }
        //neu_lymf
        //neu_cd19
        //neu_cd4
        //neu_cd4
        //cd19_cd4

        validateState(validationErrors);

        var state = stateBuilder.build();
        Optional<TChartState> stateOpt = stateRepository.findState(state.getDiagnosis(), state);

        return stateOpt.orElseGet(() -> stateRepository.save(state));
    }

}
