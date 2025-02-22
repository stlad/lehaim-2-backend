package ru.vaganov.lehaim.recommendation.charts.tcell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.exceptions.ChartStateException;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;


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
    void singleStateTwoPatients() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = tCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 1 состояние
        var recommendation2 = tCellChartService.getRecommendation(patient2, results2);
        Assertions.assertNotNull(recommendation2);
        Assertions.assertEquals("рек 1", recommendation2.getRecommendation());
        Assertions.assertEquals(1, stateRepository.count());
    }


    @Test
    void multipleStateTwoPatients() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = tCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 2.)
                .withResult(MostUsedParameters.CD8.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 2 состояния
        var recommendation2 = tCellChartService.getRecommendation(patient2, results2);
        Assertions.assertNull(recommendation2);
        Assertions.assertEquals(2, stateRepository.count());
    }

    @Test
    void multipleStateTwoPatientsDifferentDiagnosis() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = tCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C64").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.CD8.getId(), 2.)
                .withResult(MostUsedParameters.NEU.getId(), 5.)
                .withResult(MostUsedParameters.LYMF.getId(), 5.)
                .withResult(MostUsedParameters.CD3.getId(), 0.5)
                .withResult(MostUsedParameters.CD4.getId(), 0.5)
                .withResult(MostUsedParameters.CD8.getId(), 0.5)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 2 состояния c разными диагнозами
        var recommendation2 = tCellChartService.getRecommendation(patient2, results2);
        Assertions.assertNull(recommendation2);
        Assertions.assertEquals(2, stateRepository.count());
    }

    @Test
    void saveRecommendation_Throws_whenNoParams() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.MON.getId(), 1.)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние

        Assertions.assertThrows(ChartStateException.class,
                () -> tCellChartService.saveRecommendation(
                        testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1)
        );
    }


}