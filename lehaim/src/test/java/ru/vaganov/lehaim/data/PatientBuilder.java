package ru.vaganov.lehaim.data;

import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;

import java.time.LocalDate;

public class PatientBuilder {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;

    private Patient patient;

    public PatientBuilder(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
        patient = Patient.builder()
                .name("Иван")
                .lastname("Иванов")
                .patronymic("Иванович")
                .birthdate(LocalDate.parse("1970-01-01"))
                .diagnosis(diagnosisRepository.findByCode("C50").orElseThrow())
                .build();
    }

    public PatientBuilder withName(String name) {
        patient.setName(name);
        return this;
    }

    public PatientBuilder withLastname(String lastName) {
        patient.setLastname(lastName);
        return this;
    }

    public PatientBuilder withPatronymic(String patronymic) {
        patient.setPatronymic(patronymic);
        return this;
    }

    public PatientBuilder withDiagnosis(String diagnosisCode) {
        patient.setDiagnosis(diagnosisRepository.findByCode(diagnosisCode).orElseThrow());
        return this;
    }

    public Patient buildAndSave() {
        return patientRepository.save(this.patient);
    }
}
