package ru.vaganov.lehaim.recommendation.charts.bcell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.exceptions.ChartStateException;
import ru.vaganov.lehaim.oncotest.repository.OncologicalTestRepository;
import ru.vaganov.lehaim.oncotest.repository.ParameterResultRepository;

class BCellChartServiceTest extends BaseContextTest {
    @Autowired
    private BCellChartService bCellChartService;
    @Autowired
    private BChartStateRepository stateRepository;
    @Autowired
    private ParameterResultRepository parameterResultRepository;
    @Autowired
    private OncologicalTestRepository oncologicalTestRepository;


    @Test
    void getChart() {
        Assertions.assertEquals(ChartType.B_CELL, bCellChartService.getChart());
    }

    @Test
    void singleStateTwoPatients() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 10.)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = bCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.B_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 10.)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 1 состояние
        var recommendation2 = bCellChartService.getRecommendation(patient2, results2);
        Assertions.assertNotNull(recommendation2);
        Assertions.assertEquals("рек 1", recommendation2.getRecommendation());
        Assertions.assertEquals(1, stateRepository.count());
    }

    @Test
    void multipleStateTwoPatients() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 4.7)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = bCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 10.)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 2 состояния
        var recommendation2 = bCellChartService.getRecommendation(patient2, results2);
        Assertions.assertNull(recommendation2);
        Assertions.assertEquals(2, stateRepository.count());
    }

    @Test
    void multipleStateTwoPatientsDifferentDiagnosis() {
        var patient1 = testData.patient().withDiagnosis("C50").buildAndSave();
        var test1 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 10.)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results1 = parameterResultRepository.findByAttachedTest_Id(test1.getId());

        //1 рекомендация + 1 состояние
        var recommendation = bCellChartService.saveRecommendation(
                testData.recommendation().withRecommendation("рек 1").withChart(ChartType.T_CELL).build(), patient1, results1);
        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(1, stateRepository.count());


        var patient2 = testData.patient().withDiagnosis("C64").buildAndSave();
        var test2 = testData.oncologicalTest().withPatient(patient1)
                .withResult(MostUsedParameters.IgM.getId(), 10.)
                .withResult(MostUsedParameters.IgA.getId(), 1.)
                .withResult(MostUsedParameters.IgG.getId(), 1.)
                .withResult(MostUsedParameters.NEU.getId(), 1.)
                .withResult(MostUsedParameters.LYMF.getId(), 1.)
                .withResult(MostUsedParameters.CD19.getId(), 1.)
                .withResult(MostUsedParameters.CD4.getId(), 1.)
                .withResult(MostUsedParameters.TNK_CELLS.getId(), 1.)
                .buildAndSave();
        var results2 = parameterResultRepository.findByAttachedTest_Id(test2.getId());

        //1 рекомендация + 2 состояния c разными диагнозами
        var recommendation2 = bCellChartService.getRecommendation(patient2, results2);
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
                () -> bCellChartService.saveRecommendation(
                        testData.recommendation().withRecommendation("рек 1").withChart(ChartType.B_CELL).build(), patient1, results1)
        );
    }


}