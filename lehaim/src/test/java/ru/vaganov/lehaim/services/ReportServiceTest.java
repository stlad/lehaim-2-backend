package ru.vaganov.lehaim.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.oncotest.repository.OncologicalTestRepository;
import ru.vaganov.lehaim.report.dto.ReportAverageTableType;
import ru.vaganov.lehaim.report.dto.TestSeason;
import ru.vaganov.lehaim.report.service.ReportService;

class ReportServiceTest extends BaseContextTest {

    @Autowired
    private ReportService reportService;
    @Autowired
    private OncologicalTestRepository oncologicalTestRepository;

    @Test
    void createReport() {
        var pat = testData.patient().buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L, 200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-05-01")
                .withResult(1L, 30.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(25., param.getValue());
    }

    @Test
    void createReportWithRadiationTherapy() {
        var pat = testData.patient().withTherapy("2018-04-01", "2018-06-01")
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L, 200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-05-01")
                .withResult(1L, 30.).withPatient(pat)
                .buildAndSave();
        var spring3 = testData.oncologicalTest()
                .withDate("2018-04-01")
                .withResult(1L, 70.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();

        //TODO Уточнить у аналитков:
        //Если при расчете по всем анализам, мы выбрасывем период ЛТ - то 20
        Assertions.assertEquals(20., param.getValue());
        //Если при расчете по всем анализам, мы оставляем все анализы - то 40
        //Assertions.assertEquals(40., param.getValue());
    }

    @Test
    void createReportWithOperation() {
        var pat = testData.patient().withOperationDate("2018-06-01")
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L, 200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-06-01")
                .withResult(1L, 30.).withPatient(pat)
                .buildAndSave();
        var spring3 = testData.oncologicalTest()
                .withDate("2018-07-01")
                .withResult(1L, 70.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.OPERATION, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(50., param.getValue());
    }

    @Test
    void createReportWithOperationAndTherapyOverlaps() {
        var pat = testData.patient()
                .withTherapy("2019-05-01", "2019-07-01")
                .withOperationDate("2019-06-01")
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L, 200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-06-01")
                .withResult(1L, 30.).withPatient(pat)
                .buildAndSave();
        var spring3 = testData.oncologicalTest()
                .withDate("2018-07-01")
                .withResult(1L, 70.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.THERAPY_AND_OPERATION_OVERLAPS, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        Assertions.assertNull(report.getPreviousResults());
        Assertions.assertNotNull(report.getErrorText());
    }

    @Test
    void createReportWithNoEndDateTherapy() {
        var pat = testData.patient()
                .withBirthday("1987-05-26")
                .withTherapy("2015-05-01", null)
                .withOperationDate("2001-01-01")
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L, 200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-06-01")
                .withResult(1L, 30.).withPatient(pat)
                .buildAndSave();
        var spring3 = testData.oncologicalTest()
                .withDate("2010-07-01")
                .withResult(1L, 70.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(70., param.getValue());
    }

    @Test
    void createReportWithNoTherapyAndOperation() {
        var pat = testData.patient()
                .withBirthday("1987-05-26")
                .withTherapy("2015-05-01", null)
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();

        pat.setOperationDate(null);
        pat.getRadiationTherapy().setStartTherapy(null);

        testData.flushDB();
        testData.clearPersistenceContext();

        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report.getReportAverageTableType());
    }


    @Test
    void case_1() {
        var pat = testData.patient()
                .withBirthday("1946-04-23")
                .withTherapy("2025-03-03", "2025-04-11")
                .buildAndSave();

        var spring0 = testData.oncologicalTest()
                .withDate("2023-07-09")
                .withResult(1L, 2.).withPatient(pat)
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2024-07-09")
                .withResult(1L, 10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2025-02-21")
                .withResult(1L, 20.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2025-03-15")
                .withResult(1L, 22.).withPatient(pat)
                .buildAndSave();
        var spring3 = testData.oncologicalTest()
                .withDate("2025-04-09")
                .withResult(1L, 23.).withPatient(pat)
                .buildAndSave();
        var spring4 = testData.oncologicalTest()
                .withDate("2025-06-14")
                .withResult(1L, 24.).withPatient(pat)
                .buildAndSave();

        pat.setOperationDate(null);

        testData.flushDB();
        testData.clearPersistenceContext();

        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report.getReportAverageTableType());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(2., param.getValue());


        var report1 = reportService.createReportByTestId(spring1.getId());
        Assertions.assertEquals(ReportAverageTableType.ALL_RESULTS, report1.getReportAverageTableType());
        var avgs1 = report1.getPreviousResults();
        var param1 = avgs1.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(6., param1.getValue());
    }
}