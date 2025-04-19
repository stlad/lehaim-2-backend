package ru.vaganov.lehaim.data;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.patient.repository.PatientRepository;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;
import ru.vaganov.lehaim.repositories.CatalogRepository;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;

@Component
@RequiredArgsConstructor
public class TestData {
    private final EntityManager entityManager;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final OncologicalTestRepository oncologicalTestRepository;
    private final CatalogRepository catalogRepository;
    private final ParameterResultRepository parameterResultRepository;
    private final RecommendationRepository recommendationRepository;

    public PatientBuilder patient() {
        return new PatientBuilder(patientRepository, diagnosisRepository, new DataGenerator());
    }

    public OncologicalTestBuilder oncologicalTest() {
        return new OncologicalTestBuilder(oncologicalTestRepository, catalogRepository, parameterResultRepository);
    }

    public RecommendationBuilder recommendation() {
        return new RecommendationBuilder(recommendationRepository);
    }

    public void flushDB() {
        entityManager.flush();
    }
}
