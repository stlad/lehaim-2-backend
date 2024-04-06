package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.*;
import ru.vaganov.ResourceServer.services.OncologicalTestService;
import ru.vaganov.ResourceServer.services.PatientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patients")
@Tag(name = "Patient API")
@Slf4j
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private OncologicalTestService oncologicalService;

    @Operation(summary = "Поиск по ID", description = "Поиск по идентификатору" )
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findPatientById(@PathVariable Long id){
        PatientDTO dto = patientService.findPatientById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск по ФИО + Дата рождения", description = "Точное совпадение всех полей пациента" )
    @GetMapping("/fullName")
    public ResponseEntity<PatientDTO> findByFullNameAndBirthdate(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam(required = false) String middlename,
            @RequestParam LocalDate birthdate){
        PatientDTO dto = patientService.findPatientByFullNameAndBirthdate(firstname, lastname, middlename, birthdate);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Добавление пациента", description = "Создание нового пациента" )
    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO dto){
        PatientDTO createdPatient = patientService.savePatient(dto);
        return new ResponseEntity<>(createdPatient, HttpStatus.OK);
    }

    @Operation(summary = "Обновление пациента", description = "Обновление непустых полей" )
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> editPatient(@PathVariable Long id, @RequestBody PatientDTO dto){
        return new ResponseEntity<>(patientService.updatePatient(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Поиск анализов по пациенту", description = "Поиск всех анализов по идентификатору пациента" )
    @GetMapping("/{patientId}/test/all")
    public ResponseEntity<List<OncologicalTestDTO>> getAllTestsByPatientId(@PathVariable Long patientId){
        return new ResponseEntity<>(oncologicalService.getAllTestByPatientId(patientId), HttpStatus.OK);
    }

    @Operation(summary = "Создание нового обследования", description = "Создание нового обследования" )
    @PostMapping("/{patientId}/test/")
    public ResponseEntity<List<ParameterResultDTO>> saveNewOncologicalTest(
            @PathVariable Long patientId,
            @RequestBody @Valid OncologicalTestRequestDTO dto){
        return new ResponseEntity<>(oncologicalService.saveNewOncologicalTest(patientId, dto), HttpStatus.OK);
    }
    @Operation(summary = "Обновление существующего обследования", description = "Обновление существующего обследования" )
    @PutMapping("/{patientId}/test/")
    public ResponseEntity<List<ParameterResultDTO>> updateOncologicalTest(
            @PathVariable Long patientId,
            @RequestBody OncologicalTestRequestDTO dto){
        return new ResponseEntity<>(oncologicalService.updateOncologicalTest(patientId,  dto), HttpStatus.OK);
    }
}
