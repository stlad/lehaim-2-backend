package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vaganov.ResourceServer.exceptions.OncologicalTestExistsException;
import ru.vaganov.ResourceServer.exceptions.OncologicalTestNotFoundException;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.exceptions.PatientNotFoundException;
import ru.vaganov.ResourceServer.mappers.OncologicalTestMapper;
import ru.vaganov.ResourceServer.mappers.ParameterResultMapper;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestRestDTO;
import ru.vaganov.ResourceServer.models.dto.ParameterResultDTO;
import ru.vaganov.ResourceServer.models.dto.ParameterResultRestDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OncologicalTestService {

    @Autowired
    private OncologicalTestRepo oncologicalTestRepo;
    @Autowired
    private OncologicalTestMapper testMapper;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ParameterResultRepo resultRepo;
    @Autowired
    private ParameterResultMapper resultMapper;
    @Autowired
    private CatalogRepo catalogRepo;


    public List<OncologicalTestDTO> getAllTestByPatientId(UUID patientId){
        return testMapper.toDto(oncologicalTestRepo.findByPatientOwner_IdOrderByTestDateDesc(patientId));
    }

    public void deleteTestById(Long testId){
        oncologicalTestRepo.deleteById(testId);
    }

    @Transactional
    public OncologicalTestRestDTO saveNewOncologicalTest(UUID ownerId, OncologicalTestRestDTO dto){
        Patient patient = patientRepo.findById(ownerId)
                .orElseThrow(()->new PatientNotFoundException(ownerId));
        if(oncologicalTestRepo.findByPatientOwner_IdAndTestDate(ownerId, dto.getTestDate()).isPresent())
            throw new OncologicalTestExistsException(ownerId, dto.getTestDate());

        OncologicalTest newTest = new OncologicalTest();
        newTest.setTestDate(dto.getTestDate());
        newTest.setPatientOwner(patient);
        newTest = oncologicalTestRepo.save(newTest);

        List<ParameterResultRestDTO> results = new ArrayList<>();
        for(ParameterResultRestDTO resDTO: dto.getResults()){
            Parameter parameter = catalogRepo.findById(resDTO.getCatalogId())
                    .orElseThrow(()-> new ParameterNotFoundException(resDTO.getCatalogId()));

            ParameterResult result = ParameterResult.builder()
                    .attachedTest(newTest)
                    .value(resDTO.getValue())
                    .parameter(parameter).build();
            results.add(resultMapper.toRestDto(resultRepo.save(result)));
        };
        return OncologicalTestRestDTO.builder()
                .id(newTest.getId())
                .testDate(newTest.getTestDate())
                .results(results)
                .build();
    }

    public List<ParameterResultDTO> getAllResultsByTestId(Long testId){
        return resultMapper.toDto(resultRepo.findByAttachedTest_Id(testId));
    }

    @Transactional
    public OncologicalTestRestDTO updateOncologicalTest(
            UUID ownerId, Long testId, OncologicalTestRestDTO requestDTO){
        OncologicalTest test = oncologicalTestRepo.findById(testId)
                .orElseThrow(()-> new OncologicalTestNotFoundException(testId));
        Optional<OncologicalTest> otherTestByDate =
                oncologicalTestRepo.findByPatientOwner_IdAndTestDate(ownerId, requestDTO.getTestDate());
        if(requestDTO.getTestDate() != null){
            if(otherTestByDate.isPresent() && !otherTestByDate.get().getId().equals( testId))
                throw new OncologicalTestExistsException(ownerId, requestDTO.getTestDate());

            test.setTestDate(requestDTO.getTestDate());
            test = oncologicalTestRepo.save(test);
        }

        for(ParameterResultRestDTO dto:requestDTO.getResults()){
            ParameterResult result = resultRepo.findByAttachedTest_PatientOwner_IdAndAttachedTest_IdAndParameter_Id(
                    ownerId, testId, dto.getCatalogId())
                    .orElseGet(()->{
                        Parameter parameter = catalogRepo.findById(dto.getCatalogId())
                                .orElseThrow(()-> new ParameterNotFoundException(dto.getCatalogId()));
                        return ParameterResult.builder()
                                .parameter(parameter)
                                .build();
                    });
            if(result.getAttachedTest() == null)
                result.setAttachedTest(test);
            result.setValue(dto.getValue());
            result = resultRepo.save(result);

        }
        List<ParameterResultRestDTO> results = resultRepo.findByAttachedTest_Id(testId)
                .stream().map(result -> resultMapper.toRestDto(result)).collect(Collectors.toList());
      
        return OncologicalTestRestDTO.builder()
                .id(test.getId())
                .testDate(test.getTestDate())
                .results(results)
                .build();
    }

    public OncologicalTestRestDTO findOncologicalTestById(UUID patientId, Long testId){
        OncologicalTest test = oncologicalTestRepo.findById(testId)
                .orElseThrow(()-> new OncologicalTestNotFoundException(testId));

        List<ParameterResult> results = resultRepo.findByAttachedTest_Id(testId);
        List<ParameterResultRestDTO> resultDtos = results.stream().map(res->resultMapper.toRestDto(res)).toList();
        return OncologicalTestRestDTO.builder()
                .id(test.getId())
                .testDate(test.getTestDate())
                .results(resultDtos)
                .build();
    }
}
