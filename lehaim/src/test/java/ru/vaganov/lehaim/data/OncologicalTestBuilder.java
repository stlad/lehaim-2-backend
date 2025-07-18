package ru.vaganov.lehaim.data;

import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;
import ru.vaganov.lehaim.oncotest.entity.OncologicalTest;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;
import ru.vaganov.lehaim.oncotest.repository.OncologicalTestRepository;
import ru.vaganov.lehaim.oncotest.repository.ParameterResultRepository;
import ru.vaganov.lehaim.patient.entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OncologicalTestBuilder {
    private final OncologicalTestRepository oncologicalTestRepository;
    private final ParameterCatalogRepository parameterCatalogRepository;
    private final ParameterResultRepository parameterResultRepository;

    private OncologicalTest oncologicalTest;
    private List<ParameterResult> results;

    public OncologicalTestBuilder(OncologicalTestRepository oncologicalTestRepository,
                                  ParameterCatalogRepository parameterCatalogRepository,
                                  ParameterResultRepository parameterResultRepository) {
        this.oncologicalTestRepository = oncologicalTestRepository;
        this.parameterCatalogRepository = parameterCatalogRepository;
        this.parameterResultRepository = parameterResultRepository;

        results = new ArrayList<>();
        oncologicalTest = OncologicalTest.builder()
                .testDate(LocalDate.parse("2015-01-01"))
                .build();


    }

    public OncologicalTestBuilder withResult(Long parameterId, Double value) {
        results.add(ParameterResult.builder()
                .parameter(parameterCatalogRepository.findById(parameterId).orElseThrow())
                .value(value)
                .attachedTest(this.oncologicalTest)
                .build());
        return this;
    }

    public OncologicalTestBuilder withAllResult() {
        this.results = parameterCatalogRepository.findAll().stream()
                .map(parameter -> ParameterResult.builder()
                        .parameter(parameter)
                        .value((parameter.getRefMin() + parameter.getRefMax()) / 2)
                        .attachedTest(this.oncologicalTest)
                        .build()).toList();
        return this;
    }

    public OncologicalTestBuilder withPatient(Patient patient) {
        this.oncologicalTest.setPatientOwner(patient);
        return this;
    }

    public OncologicalTestBuilder withDate(LocalDate date) {
        this.oncologicalTest.setTestDate(date);
        return this;
    }

    public OncologicalTestBuilder withDate(String date) {
        return this.withDate(LocalDate.parse(date));
    }

    public OncologicalTest buildAndSave() {
        this.oncologicalTest.setResults(results);
        oncologicalTestRepository.save(this.oncologicalTest);
        return oncologicalTest;
    }
}
