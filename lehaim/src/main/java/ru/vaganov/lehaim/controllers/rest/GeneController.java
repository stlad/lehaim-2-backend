package ru.vaganov.lehaim.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.dto.genes.GeneDTO;
import ru.vaganov.lehaim.services.GenesService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genes")
@Tag(name = "Genes API")
@Slf4j
@RequiredArgsConstructor
public class GeneController {
    private final GenesService genesService;

    @Operation(summary = "Получить все гены для диагноза", description = "Получить все гены для диагноза")
    @GetMapping("/{diagnosisId}")
    public ResponseEntity<List<GeneDTO>> findGenesByDiagnosis(@PathVariable Integer diagnosisId) {
        return new ResponseEntity<>(genesService.getGenesByDiagnosis(diagnosisId), HttpStatus.OK);
    }
}
