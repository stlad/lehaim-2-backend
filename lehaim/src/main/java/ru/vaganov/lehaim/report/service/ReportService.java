package ru.vaganov.lehaim.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.oncotest.entity.OncologicalTest;
import ru.vaganov.lehaim.oncotest.repository.OncologicalTestRepository;
import ru.vaganov.lehaim.patient.mapper.PatientMapper;
import ru.vaganov.lehaim.report.dto.ReportAverageTableType;
import ru.vaganov.lehaim.report.dto.ReportData;
import ru.vaganov.lehaim.report.dto.TestSeason;
import ru.vaganov.lehaim.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportService {

    private final OncologicalTestRepository oncologicalTestRepository;
    private final PatientMapper patientMapper;
    private final ReportMapper reportMapper;
    private final ReportAverageCalculator calculator;

    @Value("${lehaim.report.radiation-therapy-days-before}")
    private Integer radiationTherapyBeforeDays;

    @Value("${lehaim.report.radiation-therapy-days-after}")
    private Integer radiationTherapyAfterDays;

    @Value("${lehaim.report.operation-days-before}")
    private Integer operationBeforeDays;

    @Value("${lehaim.report.operation-days-after}")
    private Integer operationAfterDays;

    public ReportData createReportByTestId(Long testId) {
        var test = oncologicalTestRepository.findById(testId).orElseThrow();
        var reportData = init(test);
        reportData.setReportAverageTableType(defineReportType(test));
        var testToCalculateAverages = getTestByReportType(test, reportData.getReportAverageTableType());
        var averages = calculator.getCalculatedAverages(testToCalculateAverages);
        reportData.setPreviousResults(averages);
        findErrors(reportData);
        return reportData;
    }

    private ReportAverageTableType defineReportType(OncologicalTest test) {
        var radiationTherapy = test.getPatientOwner().getRadiationTherapy();
        var operationDate = test.getPatientOwner().getOperationDate();
        if (radiationTherapy != null && operationDate != null
                && DateUtils.isDateBetween(operationDate,
                radiationTherapy.getStartTherapy().orElse(LocalDate.now()),
                radiationTherapy.getEndTherapy().orElse(LocalDate.now()))
        ) {
            return ReportAverageTableType.THERAPY_AND_OPERATION_OVERLAPS;
        }

        if (radiationTherapy != null) {
            return ReportAverageTableType.RADIATION_THERAPY;
        }
        if (operationDate != null) {
            return ReportAverageTableType.OPERATION;
        }

        return ReportAverageTableType.ALL_RESULTS;
    }

    private ReportData init(OncologicalTest test) {
        var reportData = new ReportData();
        reportData.setTestId(test.getId());
        reportData.setPatient(patientMapper.toDto(test.getPatientOwner()));
        reportData.setCurrentTestDate(test.getTestDate());
        reportData.setCurrentResults(reportMapper.toReportDto(test.getResults()));
        reportData.setSeason(TestSeason.ofDate(test.getTestDate()));
        reportData.setCurrentTestNote(test.getTestNote());
        return reportData;
    }

    private List<OncologicalTest> getTestByReportType(OncologicalTest test, ReportAverageTableType reportType) {
        var prevTests = oncologicalTestRepository.findAllByPatientOwner_IdAndTestDateBefore(test.getPatientOwner().getId(),
                test.getTestDate());
        return switch (reportType) {
            case ALL_RESULTS -> getAllPrevTests(test, prevTests);
            case RADIATION_THERAPY -> getRadTherapyPrevTests(prevTests);
            case OPERATION -> getOperationPrevTests(prevTests);
            default -> null;
        };
    }


    private boolean isDuringRadiationTherapy(OncologicalTest test, Integer before, Integer after) {
        if (test.getPatientOwner().getRadiationTherapy() == null) {
            return false;
        }
        var startTherapy = test.getPatientOwner().getRadiationTherapy().getStartTherapy().orElse(LocalDate.now());
        var endTherapy = test.getPatientOwner().getRadiationTherapy().getEndTherapy().orElse(LocalDate.now());
        var testDate = test.getTestDate();

        return DateUtils.isDateBetween(testDate, startTherapy.minusDays(before), endTherapy.plusDays(after));
    }

    private boolean isDuringOperation(OncologicalTest test) {
        if (test.getPatientOwner().getOperationDate() == null) {
            return false;
        }
        var start = test.getPatientOwner().getOperationDate().minusDays(operationBeforeDays);
        var end = test.getPatientOwner().getOperationDate().plusDays(operationAfterDays);
        var testDate = test.getTestDate();

        return DateUtils.isDateBetween(testDate, start, end);
    }

    private boolean isDuringRadiationTherapy(OncologicalTest test, Integer before) {
        return isDuringRadiationTherapy(test, before, 0);
    }


    private List<OncologicalTest> getAllPrevTests(OncologicalTest currentTest, List<OncologicalTest> prevTests) {
        if (prevTests.isEmpty()) {
            return null;
        }
        if (prevTests.size() == 1) {
            return prevTests;
        }
        return prevTests.stream()
                .filter(t -> TestSeason.ofDate(currentTest.getTestDate()).equals(TestSeason.ofDate(t.getTestDate())))
                .filter(t -> !isDuringRadiationTherapy(t, radiationTherapyBeforeDays, radiationTherapyAfterDays))
                .toList();

    }

    private List<OncologicalTest> getRadTherapyPrevTests(List<OncologicalTest> prevTests) {
        if (prevTests.isEmpty()) {
            return null;
        }
        if (prevTests.size() == 1) {
            return prevTests;
        }
        return prevTests.stream()
                .filter(t -> isDuringRadiationTherapy(t, radiationTherapyBeforeDays))
                .toList();

    }

    private List<OncologicalTest> getOperationPrevTests(List<OncologicalTest> prevTests) {
        if (prevTests.isEmpty()) {
            return null;
        }
        if (prevTests.size() == 1) {
            return prevTests;
        }
        return prevTests.stream().filter(this::isDuringOperation).toList();
    }

    private void findErrors(ReportData reportData) {
        if (ReportAverageTableType.THERAPY_AND_OPERATION_OVERLAPS.equals(reportData.getReportAverageTableType())) {
            reportData.setErrorText("Таблица средних значений не может быть корректно рассчитана из-за пересечения "
                    + "операционного периода и периода лучевой терапии");
        }
    }
}


