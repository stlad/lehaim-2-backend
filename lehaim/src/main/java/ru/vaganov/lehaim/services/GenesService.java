package ru.vaganov.lehaim.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dto.genes.GeneDTO;
import ru.vaganov.lehaim.dto.genes.GeneValueDTO;
import ru.vaganov.lehaim.exceptions.DiagnosisNotFoundException;
import ru.vaganov.lehaim.exceptions.GeneNotFoundException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.mappers.GeneMapper;
import ru.vaganov.lehaim.mappers.GeneValueMapper;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.models.genes.DiagnosisGene;
import ru.vaganov.lehaim.models.genes.Gene;
import ru.vaganov.lehaim.models.genes.GeneValue;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;
import ru.vaganov.lehaim.repositories.genes.DiagnosisGeneRepository;
import ru.vaganov.lehaim.repositories.genes.GeneCatalogRepository;
import ru.vaganov.lehaim.repositories.genes.GeneValuesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenesService {

    private final GeneMapper geneMapper;
    private final DiagnosisGeneRepository diagnosisGeneRepository;
    private final GeneValuesRepository geneValuesRepository;
    private final GeneCatalogRepository geneCatalogRepository;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;

    public List<GeneDTO> getGenesByDiagnosis(Integer diagnosisId) {
        List<Gene> genes = diagnosisGeneRepository.findByDiagnosis_Id(diagnosisId)
                .stream().map(DiagnosisGene::getGene).toList();
        return geneMapper.toDTO(genes);
    }

    public Map<Integer, GeneValueDTO> getGeneValuesForPatient(UUID patientId){
        if(!patientRepository.existsById(patientId)){
            throw new PatientNotFoundException(patientId);
        }

        return geneValuesRepository.findByPatient_Id(patientId).stream().map(geneMapper::toDTO)
                .collect(Collectors.toMap(
                        GeneValueDTO::getDiagnosisId,
                        value->value
                ));
    }

    public List<Gene> getGeneCatalog(){
        return geneCatalogRepository.findAll();
    }

    private GeneValue getGeneValue(UUID patientId, Integer diagnosisId, Long geneId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new PatientNotFoundException(patientId));
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(()-> new DiagnosisNotFoundException(diagnosisId));
        Gene gene = geneCatalogRepository.findById(geneId).orElseThrow(()-> new GeneNotFoundException(geneId));

        //TODO закончить
        return null;
    }
}
