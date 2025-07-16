package ru.vaganov.lehaim.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;

class ReportServiceTest extends BaseContextTest {

    @Autowired
    private ReportService reportService;
    @Autowired
    private OncologicalTestRepository oncologicalTestRepository;

    @Test
    void createReport(){
        var pat = testData.patient().buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L,10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L,20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L,200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-05-01")
                .withResult(1L,30.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(pat.getId(), targetTest.getId());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r->r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(25., param.getValue());
    }

    @Test
    void createReportWithRadiationTherapy(){
        var pat = testData.patient().withTherapy("2018-04-01", "2018-06-01")
                .buildAndSave();
        var targetTest = testData.oncologicalTest()
                .withDate("2020-05-01")
                .withResult(1L,10.).withPatient(pat)
                .buildAndSave();
        var spring1 = testData.oncologicalTest()
                .withDate("2019-05-01")
                .withResult(1L,20.).withPatient(pat)
                .buildAndSave();
        var autumn1 = testData.oncologicalTest()
                .withDate("2018-09-01")
                .withResult(1L,200.).withPatient(pat)
                .buildAndSave();
        var spring2 = testData.oncologicalTest()
                .withDate("2018-05-01")
                .withResult(1L,30.).withPatient(pat)
                .buildAndSave();

        testData.clearPersistenceContext();


        var report = reportService.createReportByTestId(pat.getId(), targetTest.getId());
        var avgs = report.getPreviousResults();
        var param = avgs.stream().filter(r->r.getParameter().getId().equals(1L)).findAny().orElseThrow();
        Assertions.assertEquals(20., param.getValue());
    }
}