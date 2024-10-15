package ru.vaganov.lehaim.repositories.genes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.genes.DiagnosisGene;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisGeneRepository extends JpaRepository<DiagnosisGene, Long> {
    List<DiagnosisGene> findByDiagnosis_Id(Integer id);

    Optional<DiagnosisGene> findByDiagnosis_IdAndGene_Id(Integer diagnosisId, Long geneId);

}
