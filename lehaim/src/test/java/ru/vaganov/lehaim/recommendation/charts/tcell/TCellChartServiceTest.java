package ru.vaganov.lehaim.recommendation.charts.tcell;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;


class TCellChartServiceTest extends BaseContextTest {

    @Autowired
    private TCellChartService tCellChartService;
    @Autowired
    private TChartStateRepository stateRepository;
    @Autowired
    private ParameterResultRepository parameterResultRepository;
    @Autowired
    private OncologicalTestRepository oncologicalTestRepository;


    @Test
    void getChart() {
        Assertions.assertEquals(ChartType.T_CELL, tCellChartService.getChart());
    }

    @Test
    void getRec(){
        var patient = testData.patient().withDiagnosis("C50").buildAndSave();
        var test = testData.oncologicalTest().withPatient(patient)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .buildAndSave();
        var results = parameterResultRepository.findByAttachedTest_Id(test.getId());

        var recommendation = tCellChartService.getRecommendation(patient, results);
        Assertions.assertNotNull(recommendation);

        var state = stateRepository.findAll().stream().findAny().orElseThrow();
        Assertions.assertEquals(recommendation, state.getRecommendation());
        Assertions.assertEquals(-1,state.getCd4compareCd8());
    }
}