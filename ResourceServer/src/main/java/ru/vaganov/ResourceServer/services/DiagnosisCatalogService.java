package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.mappers.DiagnosisMapper;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.dto.DiagnosisDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;

import java.util.List;

@Service @Slf4j
public class DiagnosisCatalogService {

    @Autowired
    private DiagnosisRepo diagnosisRepo;
    @Autowired
    private DiagnosisMapper diagnosisMapper;

    public List<DiagnosisDTO> findAll(){
        return diagnosisMapper.toDto(diagnosisRepo.findAll());
    }


    public DiagnosisDTO findById(Integer id){
        return diagnosisMapper.toDto(diagnosisRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cannot find diagnosis with id: "+id)));
    }
}
