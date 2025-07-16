package ru.vaganov.lehaim.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.TestSeason;
import ru.vaganov.lehaim.dto.ParameterDTO;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestRestDTO;
import ru.vaganov.lehaim.mappers.ParameterResultMapper;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.patient.service.PatientService;
import ru.vaganov.lehaim.report.dto.ReportData;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.services.CatalogService;
import ru.vaganov.lehaim.services.OncologicalTestService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final PatientService patientService;
    private final OncologicalTestService oncologicalTestService;
    private final OncologicalTestRepository oncologicalTestRepository;
    private final ParameterResultMapper resultMapper;
    private final CatalogService catalogService;

    @Value("${lehaim.report.exclude-radiation-therapy-days-before}")
    private Integer radiationTherapyBeforeDays;

    @Value("${lehaim.report.exclude-radiation-therapy-days-after}")
    private Integer radiationTherapyAfterDays;

    public ReportData createReportByTestId(UUID patientId, Long testId) {
        ReportData reportData = new ReportData();
        OncologicalTestRestDTO test = oncologicalTestService.findOncologicalTestById(patientId, testId);
        reportData.setTestId(testId);
        reportData.setPatient(patientService.findPatientById(patientId));
        reportData.setCurrentTestDate(test.getTestDate());
        reportData.setCurrentResults(oncologicalTestService.getAllResultsByTestId(testId));
        reportData.setSeason(TestSeason.ofDate(test.getTestDate()));
        reportData.setCurrentTestNote(test.getTestNote());

        List<OncologicalTest> prevTests = oncologicalTestRepository.findAllByPatientOwner_IdAndTestDateBefore(patientId, test.getTestDate());

        reportData.setPreviousResults(calculatePreviousResults(test, prevTests));
        reportData.setRadiationTherapyResults(calculateRadiationTherapyResults(testId, prevTests));
        return reportData;
    }

    private List<ParameterResultDTO> calculateRadiationTherapyResults(Long testId,
                                                                      List<OncologicalTest> prevTests) {
        var test = oncologicalTestRepository.findById(testId).orElseThrow();
        var radiationTherapy = test.getPatientOwner().getRadiationTherapy();
        if (radiationTherapy == null) {
            return null;
        }

        Map<Long, List<Double>> aggregates = new HashMap<>();
        prevTests.stream()
                .filter(t -> isDuringRadiationTherapy(t, radiationTherapyBeforeDays))
                .forEach(t -> calculateAvgs(t, aggregates));

        return calculateAvgs(aggregates);
    }

    private List<ParameterResultDTO> calculatePreviousResults(OncologicalTestRestDTO currentTest,
                                                              List<OncologicalTest> prevTests) {
        if (prevTests.isEmpty()) {
            return null;
        }
        if (prevTests.size() == 1) {
            return prevTests.get(0).getResults().stream().map(resultMapper::toDto).toList();
        }
        Map<Long, List<Double>> aggregates = new HashMap<>();
        prevTests.stream()
                .filter(t -> TestSeason.ofDate(currentTest.getTestDate()).equals(TestSeason.ofDate(t.getTestDate())))
                .filter(t -> !isDuringRadiationTherapy(t, radiationTherapyBeforeDays, radiationTherapyAfterDays))
                .forEach(t -> calculateAvgs(t, aggregates));

        return calculateAvgs(aggregates);
    }

    private void calculateAvgs(OncologicalTest t, Map<Long, List<Double>> aggregatesContainer) {
        t.getResults()
                .stream().map(resultMapper::toRestDto)
                .forEach(result -> {
                    if (!aggregatesContainer.containsKey(result.getCatalogId()))
                        aggregatesContainer.put(result.getCatalogId(), new ArrayList<>());
                    aggregatesContainer.get(result.getCatalogId()).add(result.getValue());

                });
    }

    private List<ParameterResultDTO> calculateAvgs(Map<Long, List<Double>> aggregates) {
        return aggregates.entrySet().stream().map(entrySet -> {
            ParameterResultDTO dto = new ParameterResultDTO();
            ParameterDTO parameter = catalogService.getDtoById(entrySet.getKey());
            dto.setParameter(parameter);
            double sum = entrySet.getValue().stream().reduce(0d, Double::sum);
            double average = sum / entrySet.getValue().size();
            dto.setValue(average);
            return dto;
        }).toList();
    }


    private boolean isDuringRadiationTherapy(OncologicalTest test, Integer before, Integer after) {
        if (test.getPatientOwner().getRadiationTherapy() == null) {
            return false;
        }
        var startTherapy = test.getPatientOwner().getRadiationTherapy().getStartTherapy();
        var endTherapy = test.getPatientOwner().getRadiationTherapy().getEndTherapy();
        var testDate = test.getTestDate();

        return testDate.isAfter(startTherapy.minusDays(before))
                && testDate.isBefore(endTherapy.plusDays(after));
    }

    boolean isDuringRadiationTherapy(OncologicalTest test, Integer before) {
        return isDuringRadiationTherapy(test, before, 0);
    }
}
