package ru.vaganov.lehaim.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dto.recommendation.RecommendationDTO;
import ru.vaganov.lehaim.services.recommendation.RecommendationService;

import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recommendations")
@Tag(name = "Рекомендации")
@Slf4j
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;


    @Operation(summary = "Поиск по рекомендаций, подходящих по обследованию",
            description = "Поиск рекомендаций подходящих для обследования")
    @GetMapping("/{testId}")
    public ResponseEntity<Map<ChartType, RecommendationDTO>> findAllRecommendationByTest(@PathVariable Long testId) {
        Map<ChartType, RecommendationDTO> dto = recommendationService.getRecommendation(testId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Сохранение рекомедации",
            description = "Сохранение рекомедации на основе текщего обследоваия")
    @PostMapping("/{testId}/{chartType}")
    public ResponseEntity<RecommendationDTO> saveReccomendation(@PathVariable(required = true) Long testId,
                                                                @PathVariable(required = true) ChartType chartType,
                                                                @RequestBody RecommendationDTO dto) {
        RecommendationDTO newDto = recommendationService.saveNewRecommendation(testId, chartType, dto);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @Operation(summary = "Обновление рекомендации",
            description = "Обновление рекомендации")
    @PutMapping("edit/{recommendationId}")
    public ResponseEntity<RecommendationDTO> editReccomendation(@PathVariable UUID recommendationId,
                                                                @RequestBody RecommendationDTO dto) {
        RecommendationDTO newDto = recommendationService.editRecommendation(recommendationId, dto);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск по рекомендаций по ID",
            description = "Поиск по рекомендаций по ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<RecommendationDTO> findById(@PathVariable UUID id) {
        RecommendationDTO dto = recommendationService.getRecommendationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Каталог типов графиков",
            description = "Каталог типов графиков")
    @GetMapping("/chartTypes")
    public ResponseEntity<ChartType[]> findallChartTypes() {
        return new ResponseEntity<>(ChartType.values(), HttpStatus.OK);
    }

    @Operation(summary = "Получение рекомендации для обследования по типу",
            description = "Получение рекомендации для обследования по типу",
            deprecated = true)
    @GetMapping("/{testId}/{chartType}")
    @Deprecated(forRemoval = true)
    public ResponseEntity<RecommendationDTO> getRecommendationForChart(@PathVariable Long testId,
                                                                       @PathVariable ChartType chartType) {
        RecommendationDTO newDto = recommendationService.getRecommendation(testId, chartType);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }
}
