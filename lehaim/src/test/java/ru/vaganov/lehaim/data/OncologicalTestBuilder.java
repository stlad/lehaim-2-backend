package ru.vaganov.lehaim.data;

import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OncologicalTestBuilder {
    private final OncologicalTestRepository oncologicalTestRepository;
    private final CatalogRepository catalogRepository;
    private final ParameterResultRepository parameterResultRepository;

    private OncologicalTest oncologicalTest;
    private List<ParameterResult> results;

    public OncologicalTestBuilder(OncologicalTestRepository oncologicalTestRepository,
                                  CatalogRepository catalogRepository,
                                  ParameterResultRepository parameterResultRepository) {
        this.oncologicalTestRepository = oncologicalTestRepository;
        this.catalogRepository = catalogRepository;
        this.parameterResultRepository = parameterResultRepository;

        results = new ArrayList<>();
        oncologicalTest = OncologicalTest.builder()
                .testDate(LocalDate.parse("2015-01-01"))
                .build();


    }

    public OncologicalTestBuilder withResult(Long parameterId, Double value) {
        results.add(ParameterResult.builder()
                .parameter(catalogRepository.findById(parameterId).orElseThrow())
                .value(value)
                .attachedTest(this.oncologicalTest)
                .build());
        return this;
    }

    public OncologicalTestBuilder withAllResult() {
        this.results = catalogRepository.findAll().stream()
                .map(parameter -> ParameterResult.builder()
                            .parameter(parameter)
                            .value((parameter.getRefMin() + parameter.getRefMax()) / 2)
                            .attachedTest(this.oncologicalTest)
                            .build()).toList();
        return this;
    }

    public OncologicalTestBuilder withPatient(Patient patient){
        this.oncologicalTest.setPatientOwner(patient);
        return this;
    }

    public OncologicalTest buildAndSave() {
        oncologicalTestRepository.save(this.oncologicalTest);
        parameterResultRepository.saveAll(this.results);
        return oncologicalTest;
    }
}
