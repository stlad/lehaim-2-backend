package ru.vaganov.lehaim.oncotest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.oncotest.dto.ParameterResultDTO;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tests")
@Tag(name = "Результаты обследований")
@Slf4j
@RequiredArgsConstructor
public class OncologicalTestsController {

    private final OncologicalTestService oncologicalService;

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
