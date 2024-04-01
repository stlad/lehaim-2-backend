package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.services.OncologicalTestService;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/results")
@Tag(name = "Oncological Tests API")
@Slf4j
public class ResultsController {

    @Autowired private OncologicalTestService oncologicalService;

    @Operation(summary = "Поиск анализов по пациенту", description = "Поиск всех анализов по идентификатору пациента" )
    @GetMapping("/tests/{patientId}/all")
    public ResponseEntity<List<OncologicalTestDTO>> getAllTestsByPatientId(@PathVariable Long patientId){
        return new ResponseEntity<>(oncologicalService.getAllTestByPatientId(patientId), HttpStatus.OK);
    }

    @Operation(summary = "Поиск анализов по пациенту", description = "Поиск всех анализов по идентификатору пациента" )
    @DeleteMapping("/tests/{testId}")
    public ResponseEntity deleteTestByTestId(@PathVariable Long testId){
        oncologicalService.deleteTestById(testId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
