package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.DiagnosisDTO;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;
import ru.vaganov.ResourceServer.services.DiagnosisCatalogService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/diagnoses")
@Tag(name = "Diagnosis API")
@Slf4j
public class DiagnosisController {

    @Autowired
    private DiagnosisCatalogService diagnosisService;

    @Operation(summary = "Поиск по ID", description = "Поиск по идентификатору" )
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisDTO> findDiagnosisById(@PathVariable Integer id){
        return new ResponseEntity<>(diagnosisService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Получить весь кактлог диагнозов", description = "Получить весь кактлог диагнозов" )
    @GetMapping("/all")
    public ResponseEntity<List<DiagnosisDTO>> findDiagnosisById(){
        return new ResponseEntity<>(diagnosisService.findAll(), HttpStatus.OK);
    }

}
