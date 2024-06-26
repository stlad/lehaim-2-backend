package ru.vaganov.ResourceServer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.enums.TestSeason;
import ru.vaganov.ResourceServer.mappers.ParameterMapper;
import ru.vaganov.ResourceServer.mappers.ParameterResultMapper;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.dto.*;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {

    private PatientService patientService;
    private OncologicalTestService oncologicalTestService;
    private OncologicalTestRepo oncologicalTestRepo;
    private ParameterResultRepo resultRepo;
    private ParameterResultMapper resultMapper;
    private CatalogService catalogService;
    private ParameterMapper parameterMapper;

    public ReportData createReportByTestId(UUID patientId, Long testId){
        ReportData reportData = new ReportData();
        OncologicalTestRestDTO test = oncologicalTestService.findOncologicalTestById(patientId, testId);
        reportData.setTestId(testId);
        reportData.setPatient(patientService.findPatientById(patientId));
        reportData.setCurrentTestDate(test.getTestDate());
        reportData.setCurrentResults(oncologicalTestService.getAllResultsByTestId(testId));
        reportData.setSeason(TestSeason.ofDate(test.getTestDate()));

        List<OncologicalTest> prevTests = oncologicalTestRepo.findAllByPatientOwner_IdAndTestDateBefore(patientId, test.getTestDate());

        if(prevTests.size() == 0){
            return reportData;
        }

        if(prevTests.size() == 1){
            List<ParameterResultDTO> prevResults = resultRepo.findByAttachedTest_Id(prevTests.get(0).getId())
                    .stream().map(resultMapper::toDto).toList();
            reportData.setPreviousResults(prevResults);
            return reportData;
        }
        Map<Long, List<Double>> aggregates = new HashMap<>();
        prevTests.stream()
                .filter(t-> TestSeason.ofDate(test.getTestDate()).equals(TestSeason.ofDate(t.getTestDate())))
                .forEach(t->
                        resultRepo.findByAttachedTest_Id(t.getId())
                                .stream().map(resultMapper::toRestDto)
                                .forEach(result-> {
                                    if(!aggregates.containsKey(result.getCatalogId()))
                                        aggregates.put(result.getCatalogId(), new ArrayList<>());
                                    aggregates.get(result.getCatalogId()).add(result.getValue());

                                })
                );

        reportData.setPreviousResults(calculateAvgs(aggregates));
        return reportData;
    }


    private List<ParameterResultDTO> calculateAvgs(Map<Long, List<Double>> aggregates){
        return aggregates.entrySet().stream().map(entrySet ->{
            ParameterResultDTO dto = new ParameterResultDTO();
            ParameterDTO parameter = catalogService.getDtoById(entrySet.getKey());
            dto.setParameter(parameter);
            double sum = entrySet.getValue().stream().reduce(0d,Double::sum);
            double average = sum / entrySet.getValue().size();
            dto.setValue(average);
            return dto;
        }).toList();
    }
}
