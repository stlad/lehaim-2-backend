package ru.vaganov.lehaim.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vaganov.lehaim.dto.genes.GeneDTO;
import ru.vaganov.lehaim.dto.genes.GeneValueInputDTO;
import ru.vaganov.lehaim.dto.genes.GeneValueInputListDTO;
import ru.vaganov.lehaim.dto.genes.GeneValueOutputListDTO;
import ru.vaganov.lehaim.exceptions.GeneNotFoundException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.mappers.GeneMapper;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.models.genes.DiagnosisGene;
import ru.vaganov.lehaim.models.genes.Gene;
import ru.vaganov.lehaim.models.genes.GeneValue;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;
import ru.vaganov.lehaim.repositories.genes.DiagnosisGeneRepository;
import ru.vaganov.lehaim.repositories.genes.GeneCatalogRepository;
import ru.vaganov.lehaim.repositories.genes.GeneValuesRepository;

import java.util.ArrayList;
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

    public Map<Integer, GeneValueInputDTO> getGeneValuesForPatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }

        return geneValuesRepository.findByPatient_Id(patientId).stream().map(geneMapper::toDTO)
                .collect(Collectors.toMap(
                        GeneValueInputDTO::getDiagnosisId,
                        value -> value
                ));
    }

    public List<Gene> getGeneCatalog() {
        return geneCatalogRepository.findAll();
    }

    @Transactional
    public GeneValueOutputListDTO saveGeneValues(UUID patientId, GeneValueInputListDTO dto) {
        if (dto.getPatientId() != null) {
            patientId = dto.getPatientId();
        }
        List<GeneValue> geneValuesResult = new ArrayList<>();
        for(GeneValueInputDTO value : dto.getValues()){
            GeneValue target = getGeneValue(patientId, value.getDiagnosisId(), value.getGeneId());
            target.setGeneValue(value.getGeneValue());
        }
        return geneMapper.toListOutputDto(patientId, geneValuesResult);
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
