package ru.vaganov.lehaim.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.exceptions.DiagnosisNotFoundException;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.patient.service.PatientService;
import ru.vaganov.lehaim.patient.repository.PatientRepository;

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

    @Test
    @DisplayName("Создание пациента: отсутсвие лучевой терапии")
    void createPatientWithoutTherapy() {
        var dto = PatientDTO.builder()
                .birthdate("2020-01-01")
                .diagnosisId(2)
                .radiationTherapy(null)
                .build();

        var response = patientService.savePatient(dto);

        Assertions.assertNull(response.getRadiationTherapy());
    }

    @Test
    @DisplayName("Создание пациента: с лучевой терапии")
    void createPatientWithTherapy() {
        var dto = PatientDTO.builder()
                .birthdate("1980-01-01")
                .diagnosisId(2)
                .radiationTherapy(PatientRadiationTherapyDTO.builder()
                        .startTherapy("2020-01-01")
                        .endTherapy("")
                        .build())
                .build();

        var response = patientService.savePatient(dto);

        Assertions.assertNotNull(response.getRadiationTherapy());
        Assertions.assertEquals("2020-01-01", response.getRadiationTherapy().getStartTherapy());
        Assertions.assertNull(response.getRadiationTherapy().getEndTherapy());
        Assertions.assertNull(response.getRadiationTherapy().getComment());
    }

    @Test
    @DisplayName("Обновление пациента: добавление терапии")
    void updatePatientCreateTherapy() {
        var patient = testData.patient()
                .withFullName("Ivanov", "Ivan", "Ivanovich")
                .withBirthday(LocalDate.parse("1970-01-01"))
                .withDiagnosis(1)
                .buildAndSave();

        Assertions.assertNull(patient.getRadiationTherapy());

        var dto = PatientDTO.builder()
                .radiationTherapy(
                        PatientRadiationTherapyDTO.builder()
                                .startTherapy("2020-01-01")
                                .endTherapy("2023-01-01")
                                .comment("comment")
                                .build()
                )
                .build();

        var result = patientService.updatePatient(patient.getId(), dto);

        Assertions.assertNotNull(patient.getRadiationTherapy());
        Assertions.assertEquals("2020-01-01", result.getRadiationTherapy().getStartTherapy());
        Assertions.assertEquals("2023-01-01", result.getRadiationTherapy().getEndTherapy());
        Assertions.assertEquals("comment", result.getRadiationTherapy().getComment());
    }

    @Test
    @DisplayName("Обновление пациента: удаление дат лучевой терапии")
    void updatePatientDeleteTherapyDate() {
        var patient = testData.patient()
                .withFullName("Ivanov", "Ivan", "Ivanovich")
                .withBirthday(LocalDate.parse("1970-01-01"))
                .withDiagnosis(1)
                .withTherapy(LocalDate.parse("2020-01-01"), LocalDate.parse("2023-01-01"))
                .buildAndSave();

        Assertions.assertNotNull(patient.getRadiationTherapy());

        var dto = PatientDTO.builder()
                .radiationTherapy(
                        PatientRadiationTherapyDTO.builder()
                                .startTherapy("")
                                .endTherapy(null)
                                .build()
                )
                .build();

        var result = patientService.updatePatient(patient.getId(), dto);

        Assertions.assertNotNull(patient.getRadiationTherapy());
        Assertions.assertNull(result.getRadiationTherapy().getStartTherapy());
        Assertions.assertEquals("2023-01-01", result.getRadiationTherapy().getEndTherapy());
    }

    @Test
    @DisplayName("Обновление пациента: изменение ФИО на уже существующего")
    void updatePatient_throws_whenUpdateToExisting(){
        var patient1 = testData.patient().withFullName("Иванов","Иван","Иванович")
                .withBirthday(LocalDate.parse("1970-01-01")).buildAndSave();
        var patient2 = testData.patient().withFullName("Андреев","Андрей","Андреевич")
                .withBirthday(LocalDate.parse("1980-01-01")).buildAndSave();

        var newPatientDTO = PatientDTO.builder().name("Андрей").lastname("Андреев").patronymic("Андреевич")
                .birthdate(LocalDate.parse("1980-01-01").toString()).build();

        patientService.updatePatient(patient1.getId(), newPatientDTO);
    }

    @Test
    @DisplayName("Невозможно создать пациента с одинаковыми ФИО + ДР")
    void savePatient_throws_whenPatientExists(){
        testData.patient().withFullName("Иванов","Иван","Иванович")
                .withBirthday(LocalDate.parse("1970-01-01")).buildAndSave();

        testData.flushDB();

        var newPatientDTO = PatientDTO.builder().name("Иванов").lastname("Иван").patronymic("Иванович")
                .birthdate(LocalDate.parse("1980-01-01").toString()).build();

        patientService.savePatient(newPatientDTO);

    }

}