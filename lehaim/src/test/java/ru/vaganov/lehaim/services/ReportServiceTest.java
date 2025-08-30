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
        Assertions.assertEquals(ReportAverageTableType.RADIATION_THERAPY, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(50., param.getValue());

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
                .withDate("2018-07-01")
                .withResult(1L, 70.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(targetTest.getId());
        Assertions.assertEquals(ReportAverageTableType.RADIATION_THERAPY, report.getReportAverageTableType());
        Assertions.assertEquals(TestSeason.SPRING, report.getSeason());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r -> r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(80., param.getValue());
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
}