package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.DiagnosisNotFoundException;
import ru.vaganov.ResourceServer.mappers.DiagnosisMapper;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.dto.DiagnosisDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class DiagnosisCatalogService {

    @Autowired
    private DiagnosisRepo diagnosisRepo;
    @Autowired
    private DiagnosisMapper diagnosisMapper;

    public List<DiagnosisDTO> findAll(){
        LinkedList<Diagnosis> diagnosisList = diagnosisRepo.findAll();
        diagnosisList.addFirst(diagnosisList.removeLast());
        return diagnosisMapper.toDto(diagnosisList.stream().toList());
    }


    public DiagnosisDTO findById(Integer id){
        return diagnosisMapper.toDto(diagnosisRepo.findById(id)
                .orElseThrow(()-> new DiagnosisNotFoundException(id)));
    }
}
