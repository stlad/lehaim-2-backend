package ru.vaganov.lehaim.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.exceptions.DiagnosisNotFoundException;
import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.patient.service.PatientService;
import ru.vaganov.lehaim.repositories.PatientRepository;

import java.time.LocalDate;

class PatientServiceTest extends BaseContextTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;


    @Test
    @DisplayName("Сохрананение нового пациента")
    void savePatient() {
        var dto = PatientDTO.builder()
                .name("Ivan")
                .birthdate("1970-01-01")
                .diagnosisId(null)
                .build();

        var result = patientService.savePatient(dto);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getBirthdate(), result.getBirthdate());
        Assertions.assertNull(result.getDiagnosisId());
        Assertions.assertNull(result.getDeathdate());
    }

    @Test
    @DisplayName("Сохрананение нового пациента с даигнозом")
    void savePatientWithDiagnosis() {
        var dto = PatientDTO.builder()
                .name("Ivan")
                .birthdate("1970-01-01")
                .diagnosisId(1)
                .build();

        var result = patientService.savePatient(dto);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getBirthdate(), result.getBirthdate());
        Assertions.assertEquals(dto.getDiagnosisId(), result.getDiagnosisId());
        Assertions.assertNull(result.getDeathdate());
    }

    @Test
    @DisplayName("Сохрананение нового пациента с несуществующим даигнозом")
    void savePatientWithNotExistingDiagnosis() {
        var dto = PatientDTO.builder()
                .name("Ivan")
                .birthdate("1970-01-01")
                .diagnosisId(-1)
                .build();

        Assertions.assertThrows(DiagnosisNotFoundException.class, () -> patientService.savePatient(dto));
    }

    @Test
    @DisplayName("Обновление пациента")
    void updatePatient() {
        var patient = testData.patient()
                .withFullName("Ivanov", "Ivan", "Ivanovich")
                .withBirthday(LocalDate.parse("1970-01-01"))
                .withDiagnosis(1)
                .buildAndSave();

        var dto = PatientDTO.builder()
                .lastname("NewLastname")
                .birthdate(null)
                .build();

        var result = patientService.updatePatient(patient.getId(), dto);

        Assertions.assertEquals("NewLastname", result.getLastname());
        Assertions.assertEquals("Ivanovich", result.getPatronymic());
        Assertions.assertEquals("Ivan", result.getName());
        Assertions.assertEquals(1, result.getDiagnosisId());
        Assertions.assertEquals("1970-01-01", result.getBirthdate());
    }

    @Test
    @DisplayName("Обновление пациента: обновление дат")
    void updatePatientDates() {
        var patient = testData.patient()
                .withFullName("Ivanov", "Ivan", "Ivanovich")
                .withBirthday(LocalDate.parse("1970-01-01"))
                .withDiagnosis(1)
                .buildAndSave();

        var dto = PatientDTO.builder()
                .birthdate("")
                .deathdate("")
                .operationDate("2020-01-01")
                .diagnosisId(2)
                .build();

        var result = patientService.updatePatient(patient.getId(), dto);

        Assertions.assertEquals(2, result.getDiagnosisId());
        Assertions.assertNull(result.getBirthdate());
        Assertions.assertEquals("2020-01-01", result.getOperationDate());
        Assertions.assertNull(result.getDeathdate());
    }

    @Test
    @DisplayName("Обновление пациента: обновление дат")
    void updatePatientWithRadiationTherapy() {
        var patient = testData.patient()
                .withFullName("Ivanov", "Ivan", "Ivanovich")
                .withBirthday(LocalDate.parse("1970-01-01"))
                .withDiagnosis(1)
                .withTherapy(LocalDate.parse("2020-01-01"), LocalDate.parse("2023-01-01"))
                .buildAndSave();

        var dto = PatientDTO.builder()
                .birthdate("")
                .deathdate("")
                .operationDate("2020-01-01")
                .diagnosisId(2)
                .build();

        var result = patientService.updatePatient(patient.getId(), dto);

        Assertions.assertEquals(2, result.getDiagnosisId());
        Assertions.assertNull(result.getBirthdate());
        Assertions.assertEquals("2020-01-01", result.getOperationDate());
        Assertions.assertNull(result.getDeathdate());
    }
}