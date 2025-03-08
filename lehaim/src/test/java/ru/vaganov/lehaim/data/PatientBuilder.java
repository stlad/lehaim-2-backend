package ru.vaganov.lehaim.data;

import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;
import ru.vaganov.lehaim.repositories.DiagnosisRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;

import java.time.LocalDate;

public class PatientBuilder {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;

    private final Patient patient;

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

    public PatientBuilder withFullName(String lastName, String name, String patronymic) {
        patient.setPatronymic(patronymic);
        patient.setLastname(lastName);
        patient.setName(name);
        return this;
    }

    public PatientBuilder withDiagnosis(String diagnosisCode) {
        patient.setDiagnosis(diagnosisRepository.findByCode(diagnosisCode).orElseThrow());
        return this;
    }

    public PatientBuilder withDiagnosis(Integer id) {
        patient.setDiagnosis(diagnosisRepository.findById(id).orElseThrow());
        return this;
    }

    public PatientBuilder withBirthday(LocalDate birthday) {
        patient.setBirthdate(birthday);
        return this;
    }

    public PatientBuilder withTherapy(LocalDate start, LocalDate end) {
        var therapy = PatientRadiationTherapy.builder()
                .startTherapy(start)
                .endTherapy(end)
                .patient(this.patient)
                .build();
        patient.setRadiationTherapy(therapy);
        return this;
    }

    public Patient buildAndSave() {
        return patientRepository.save(this.patient);
    }
}
