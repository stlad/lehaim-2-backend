package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.models.dto.ParameterResultDTO;
import ru.vaganov.ResourceServer.services.OncologicalTestService;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tests")
@Tag(name = "Oncological Tests API")
@Slf4j
public class OncologicalTestsController {

    @Autowired private OncologicalTestService oncologicalService;


    @Operation(summary = "Удаление анализа по id", description = "Удаление анализа по его id" )
    @DeleteMapping("/{testId}")
    public ResponseEntity deleteTestByTestId(@PathVariable Long testId){
        oncologicalService.deleteTestById(testId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Operation(summary = "Получение всех результатов по ID обследования", description = "Получение всех результатов по ID обследования" )
    @GetMapping("/{testId}")
    public ResponseEntity<List<ParameterResultDTO>> getAllResultsByTestId(@PathVariable Long testId){
        return new ResponseEntity<>(oncologicalService.getAllResultsByTestId(testId), HttpStatus.OK);
    }
}
