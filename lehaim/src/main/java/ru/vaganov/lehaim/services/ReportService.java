package ru.vaganov.lehaim.services;

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

        if (prevTests.isEmpty()) {
            return reportData;
        }

        if (prevTests.size() == 1) {
            List<ParameterResultDTO> prevResults = prevTests.get(0).getResults()
                    .stream().map(resultMapper::toDto).toList();
            reportData.setPreviousResults(prevResults);
            return reportData;
        }
        Map<Long, List<Double>> aggregates = new HashMap<>();
        prevTests.stream()
                .filter(t -> TestSeason.ofDate(test.getTestDate()).equals(TestSeason.ofDate(t.getTestDate())))
                .filter(this::isIncludedByRadiationTherapy)
                .forEach(t ->
                        t.getResults()
                                .stream().map(resultMapper::toRestDto)
                                .forEach(result -> {
                                    if (!aggregates.containsKey(result.getCatalogId()))
                                        aggregates.put(result.getCatalogId(), new ArrayList<>());
                                    aggregates.get(result.getCatalogId()).add(result.getValue());

                                })
                );

        reportData.setPreviousResults(calculateAvgs(aggregates));
        return reportData;
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

    /**
     * Из выборки исключаются анализы в период лучевой терапии. -14 от начала и +60 от окончания.
     */
    private boolean isIncludedByRadiationTherapy(OncologicalTest test) {
        if (test.getPatientOwner().getRadiationTherapy() == null) {
            return true;
        }
        var startTherapy = test.getPatientOwner().getRadiationTherapy().getStartTherapy();
        var endTherapy = test.getPatientOwner().getRadiationTherapy().getEndTherapy();
        var testDate = test.getTestDate();

        return !(testDate.isAfter(startTherapy.minusDays(radiationTherapyBeforeDays))
                && testDate.isBefore(endTherapy.plusDays(radiationTherapyAfterDays)));
    }
}
