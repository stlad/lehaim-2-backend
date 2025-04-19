package ru.vaganov.lehaim.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vaganov.lehaim.dto.genes.*;
import ru.vaganov.lehaim.exceptions.GeneNotFoundException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.mappers.GeneMapper;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.models.genes.DiagnosisGene;
import ru.vaganov.lehaim.models.genes.Gene;
import ru.vaganov.lehaim.models.genes.GeneValue;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.patient.repository.PatientRepository;
import ru.vaganov.lehaim.repositories.genes.DiagnosisGeneRepository;
import ru.vaganov.lehaim.repositories.genes.GeneCatalogRepository;
import ru.vaganov.lehaim.repositories.genes.GeneValuesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

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

    public Map<Integer, List<GeneValueOutputDTO>> getGeneValuesForPatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }

       return geneValuesRepository.findByPatient_Id(patientId).stream().map(geneMapper::toOutputDTO)
                .collect(groupingBy(GeneValueOutputDTO::getDiagnosisId));
    }

    public List<GeneDTO> getGeneCatalog() {
        return geneCatalogRepository.findAll().stream().map(geneMapper::toDTO).toList();
    }

    @Transactional
    public GeneValueOutputListDTO saveGeneValues(UUID patientId, GeneValueInputListDTO dto) {
        List<GeneValue> geneValuesResult = new ArrayList<>();
        for (GeneValueInputDTO value : dto.getValues()) {
            GeneValue target = getGeneValue(patientId, value.getDiagnosisId(), value.getGeneId());
            target.setGeneValue(value.getGeneValue());
            geneValuesResult.add(target);
        }
        return geneMapper.toListOutputDto(patientId, geneValuesRepository.saveAll(geneValuesResult).stream().toList());
    }

    @Transactional
    public GeneValueOutputListDTO updateGeneValues(UUID patientId, GeneValueInputListDTO dto) {
        List<GeneValue> geneValuesResult = new ArrayList<>();
        for (GeneValueInputDTO value : dto.getValues()) {
            GeneValue target = getGeneValue(patientId, value.getDiagnosisId(), value.getGeneId());
            if (target.getId() == null) {
                throw new GeneNotFoundException(patientId, value.getDiagnosisId(), value.getGeneId());
            }
            target.setGeneValue(value.getGeneValue());
            geneValuesResult.add(target);
        }
        return geneMapper.toListOutputDto(patientId, geneValuesRepository.saveAll(geneValuesResult).stream().toList());
    }

    private GeneValue getGeneValue(UUID patientId, Integer diagnosisId, Long geneId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException(patientId));
        DiagnosisGene diagnosisGene = diagnosisGeneRepository.findByDiagnosis_IdAndGene_Id(diagnosisId, geneId)
                .orElseThrow(() -> new GeneNotFoundException(diagnosisId, geneId));

        return geneValuesRepository.findByPatient_IdAndDiagnosisGene_Id(patient.getId(), diagnosisGene.getId())
                .orElseGet(() -> GeneValue.builder()
                        .diagnosisGene(diagnosisGene)
                        .patient(patient)
                        .build()
                );
    }
}
