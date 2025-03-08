package ru.vaganov.lehaim.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.TestSeason;
import ru.vaganov.lehaim.dto.ParameterDTO;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.dto.ReportData;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestRestDTO;
import ru.vaganov.lehaim.mappers.ParameterMapper;
import ru.vaganov.lehaim.mappers.ParameterResultMapper;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.patient.service.PatientService;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {

    private final PatientService patientService;
    private final OncologicalTestService oncologicalTestService;
    private final OncologicalTestRepository oncologicalTestRepository;
    private final ParameterResultRepository resultRepo;
    private final ParameterResultMapper resultMapper;
    private final CatalogService catalogService;
    private final ParameterMapper parameterMapper;

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

        if (prevTests.size() == 0) {
            return reportData;
        }

        if (prevTests.size() == 1) {
            List<ParameterResultDTO> prevResults = resultRepo.findByAttachedTest_Id(prevTests.get(0).getId())
                    .stream().map(resultMapper::toDto).toList();
            reportData.setPreviousResults(prevResults);
            return reportData;
        }
        Map<Long, List<Double>> aggregates = new HashMap<>();
        prevTests.stream()
                .filter(t -> TestSeason.ofDate(test.getTestDate()).equals(TestSeason.ofDate(t.getTestDate())))
                .forEach(t ->
                        resultRepo.findByAttachedTest_Id(t.getId())
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
}
