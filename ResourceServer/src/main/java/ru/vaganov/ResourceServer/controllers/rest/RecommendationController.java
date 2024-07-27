package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dto.recommendation.RecommendationDTO;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.services.recommendation.RecommendationService;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recommendations")
@Tag(name = "Recommendation API")
@Slf4j
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;


    @Operation(summary = "Поиск по рекомендаций, подходящих по обследованию",
            description = "Поиск рекомендаций подходящих для обследования")
    @GetMapping("/{patientId}/{testId}")
    public ResponseEntity<HashMap<ChartType, RecommendationDTO>> findAllRecommendationByTest(@PathVariable UUID patientId,
                                                                                             @PathVariable Long testId) {
        HashMap<ChartType, RecommendationDTO> dto = recommendationService.getRecommendation(patientId, testId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Сохранение рекомедации",
            description = "Сохранение рекомедации на основе текщего обследоваия")
    @PostMapping("/{patientId}/{testId}")
    public ResponseEntity<RecommendationDTO> saveReccomendation(@PathVariable UUID patientId,
                                                      @PathVariable Long testId,
                                                      @RequestBody RecommendationDTO dto) {
        RecommendationDTO newDto = recommendationService.saveNewRecommendation(patientId, testId, dto);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск по рекомендаций по ID",
            description = "Поиск по рекомендаций по ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<RecommendationDTO> findById(@PathVariable UUID id) {
        RecommendationDTO dto = recommendationService.getRecommendationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
