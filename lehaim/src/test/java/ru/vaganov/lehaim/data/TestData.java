package ru.vaganov.lehaim.data;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.repositories.*;

@Component
@RequiredArgsConstructor
public class TestData {
    private final EntityManager entityManager;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final OncologicalTestRepository oncologicalTestRepository;
    private final CatalogRepository catalogRepository;
    private final ParameterResultRepository parameterResultRepository;

    public PatientBuilder patient(){
        return new PatientBuilder(patientRepository, diagnosisRepository);
    }

    public OncologicalTestBuilder oncologicalTest(){
        return new OncologicalTestBuilder(oncologicalTestRepository, catalogRepository, parameterResultRepository);
    }

    public void flushDB(){
        entityManager.flush();
    }
}
