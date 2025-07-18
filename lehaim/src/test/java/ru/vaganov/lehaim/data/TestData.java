package ru.vaganov.lehaim.data;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.patient.repository.PatientRepository;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;
import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;
import ru.vaganov.lehaim.catalog.repository.DiagnosisRepository;
import ru.vaganov.lehaim.oncotest.repository.OncologicalTestRepository;
import ru.vaganov.lehaim.oncotest.repository.ParameterResultRepository;

@Component
@RequiredArgsConstructor
public class TestData {
    private final EntityManager entityManager;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final OncologicalTestRepository oncologicalTestRepository;
    private final ParameterCatalogRepository parameterCatalogRepository;
    private final ParameterResultRepository parameterResultRepository;
    private final RecommendationRepository recommendationRepository;

    public PatientBuilder patient() {
        return new PatientBuilder(patientRepository, diagnosisRepository, new DataGenerator());
    }

    public OncologicalTestBuilder oncologicalTest() {
        return new OncologicalTestBuilder(oncologicalTestRepository, parameterCatalogRepository, parameterResultRepository);
    }

    public RecommendationBuilder recommendation() {
        return new RecommendationBuilder(recommendationRepository);
    }

    public void flushDB() {
        entityManager.flush();
    }

    public void clearPersistenceContext() {
        flushDB();
        entityManager.clear();
    }
}
