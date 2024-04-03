package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.ValidationException;
import ru.vaganov.ResourceServer.mappers.OncologicalTestMapper;
import ru.vaganov.ResourceServer.mappers.ParameterResultMapper;
import ru.vaganov.ResourceServer.mappers.PatientMapper;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestRequestDTO;
import ru.vaganov.ResourceServer.models.dto.ParameterResultDTO;
import ru.vaganov.ResourceServer.models.dto.ParameterResultRequestDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.util.ArrayList;
import java.util.List;

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


    public List<OncologicalTestDTO> getAllTestByPatientId(Long patientId){
        return testMapper.toDto(oncologicalTestRepo.findByPatientOwner_IdOrderByTestDateDesc(patientId));
    }

    public void deleteTestById(Long testId){
        oncologicalTestRepo.deleteById(testId);
    }

    public List<ParameterResultDTO> saveNewOncologicalTest(Long ownerId, OncologicalTestRequestDTO dto){
        Patient patient = patientRepo.findById(ownerId)
                .orElseThrow(()->new EntityNotFoundException("Cannot find patient with id: "+ownerId));
        if(oncologicalTestRepo.findByPatientOwner_IdAndTestDate(ownerId, dto.getTestDate()).isPresent())
            throw new EntityExistsException("Test of patient: "+ownerId+" by "+dto.getTestDate()+" already exists");

        OncologicalTest newTest = new OncologicalTest();
        newTest.setTestDate(dto.getTestDate());
        newTest.setPatientOwner(patient);
        newTest = oncologicalTestRepo.save(newTest);

        List<ParameterResultDTO> finalResult = new ArrayList<>();
        for(ParameterResultRequestDTO resDTO: dto.getResults()){
            Parameter parameter = catalogRepo.findById(resDTO.getCatalogId())
                    .orElseThrow(()-> new EntityNotFoundException("Cannot find parameter in catalog with id: "+resDTO.getCatalogId()));

            ParameterResult result = ParameterResult.builder()
                    .attachedTest(newTest)
                    .value(resDTO.getValue())
                    .parameter(parameter).build();
            finalResult.add(resultMapper.toDto(resultRepo.save(result)));
        };
        return finalResult;
    }
}
