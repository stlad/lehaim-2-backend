package ru.vaganov.lehaim.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.exceptions.DiagnosisNotFoundException;
import ru.vaganov.lehaim.catalog.mapper.DiagnosisMapper;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;
import ru.vaganov.lehaim.catalog.dto.DiagnosisDTO;
import ru.vaganov.lehaim.catalog.repository.DiagnosisRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnosisCatalogService {
    private final DiagnosisRepository diagnosisRepository;

    private final DiagnosisMapper diagnosisMapper;

    public List<DiagnosisDTO> findAll() {
        LinkedList<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        diagnosisList.addFirst(diagnosisList.removeLast());
        return diagnosisMapper.toDto(diagnosisList.stream().toList());
    }


    public DiagnosisDTO findDtoById(Integer id) {
        return diagnosisMapper.toDto(findById(id));
    }

    public Diagnosis findById(Integer id){
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new DiagnosisNotFoundException(id));
    }
}
