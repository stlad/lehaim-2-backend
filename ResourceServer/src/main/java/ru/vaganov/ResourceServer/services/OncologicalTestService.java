package ru.vaganov.ResourceServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.mappers.OncologicalTestMapper;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;

import java.util.List;

@Service
public class OncologicalTestService {

    @Autowired
    private OncologicalTestRepo oncologicalTestRepo;
    @Autowired
    private OncologicalTestMapper testMapper;

    public List<OncologicalTestDTO> getAllTestByPatientId(Long patientId){
        return testMapper.toDto(oncologicalTestRepo.findByPatientOwner_IdOrderByTestDateDesc(patientId));
    }

    public void deleteTestById(Long testId){
        oncologicalTestRepo.deleteById(testId);
    }
}
