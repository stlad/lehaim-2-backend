package ru.vaganov.lehaim.patient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.oncotest.dto.OncologicalTestDTO;
import ru.vaganov.lehaim.oncotest.dto.OncologicalTestRestDTO;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.oncotest.OncologicalTestService;
import ru.vaganov.lehaim.patient.service.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patients")
@Tag(name = "Пациенты")
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final OncologicalTestService oncologicalService;

    @Operation(summary = "Поиск по ID", description = "Поиск по идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findPatientById(@PathVariable UUID id) {
        PatientDTO dto = patientService.findPatientById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск по ФИО + Дата рождения", description = "Точное совпадение всех полей пациента")
    @GetMapping("/fullName")
    public ResponseEntity<PatientDTO> findByFullNameAndBirthdate(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam(required = false) String middlename,
            @RequestParam LocalDate birthdate) {
        PatientDTO dto = patientService.findPatientByFullNameAndBirthdate(firstname, lastname, middlename, birthdate);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Добавление пациента", description = "Создание нового пациента")
    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO dto) {
        PatientDTO createdPatient = patientService.savePatient(dto);
        return new ResponseEntity<>(createdPatient, HttpStatus.OK);
    }

    @Operation(summary = "Обновление пациента", description = "Обновление непустых полей")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> editPatient(@PathVariable UUID id, @RequestBody PatientDTO dto) {
        return new ResponseEntity<>(patientService.updatePatient(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Поиск анализов по пациенту", description = "Поиск всех анализов по идентификатору пациента")
    @GetMapping("/{patientId}/test/all")
    public ResponseEntity<List<OncologicalTestDTO>> getAllTestsByPatientId(@PathVariable UUID patientId) {
        return new ResponseEntity<>(oncologicalService.getAllTestByPatientId(patientId), HttpStatus.OK);
    }

    @Operation(summary = "Создание нового обследования", description = "Создание нового обследования")
    @PostMapping("/{patientId}/test/")
    public ResponseEntity<OncologicalTestRestDTO> saveNewOncologicalTest(
            @PathVariable UUID patientId,
            @RequestBody @Valid OncologicalTestRestDTO dto) {
        return new ResponseEntity<>(oncologicalService.saveNewOncologicalTest(patientId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Обновление существующего обследования", description = "Обновление существующего обследования")
    @PutMapping("/{patientId}/test/{testId}")
    public ResponseEntity<OncologicalTestRestDTO> updateOncologicalTest(
            @PathVariable UUID patientId,
            @PathVariable Long testId,
            @RequestBody @Valid OncologicalTestRestDTO dto) {
        return new ResponseEntity<>(oncologicalService.updateOncologicalTest(patientId, testId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Поиск обследования по id", description = "Поиск существующего обследования по id")
    @GetMapping("/{patientId}/test/{testId}")
    public ResponseEntity<OncologicalTestRestDTO> findOncologicalTestByTestId(
            @PathVariable UUID patientId,
            @PathVariable Long testId) {
        return new ResponseEntity<>(oncologicalService.findOncologicalTestById(patientId, testId), HttpStatus.OK);
    }
}
