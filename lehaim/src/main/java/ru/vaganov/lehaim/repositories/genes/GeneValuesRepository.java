package ru.vaganov.lehaim.repositories.genes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.genes.GeneValue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GeneValuesRepository extends JpaRepository<GeneValue, Long> {

    List<GeneValue> findByPatient_Id(UUID id);

    Optional<GeneValue> findByPatient_IdAndDiagnosisGene_Diagnosis_IdAndDiagnosisGene_Gene_Id(UUID id, Integer id1, Long id2);

    default Optional<GeneValue> findGeneValueForPatient(UUID patientId, Integer diagnosisId, Long geneId){
        return findByPatient_IdAndDiagnosisGene_Diagnosis_IdAndDiagnosisGene_Gene_Id(patientId,diagnosisId,geneId);
    }

}
