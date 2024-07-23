package ru.vaganov.ResourceServer.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.RecommendationDTO;
import ru.vaganov.ResourceServer.services.RecommendationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recommendations")
@Tag(name = "Recommendation API")
@Slf4j
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Operation(summary = "Поиск по ID", description = "Поиск по идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> findRecommendationById(@PathVariable Long id) {
        RecommendationDTO dto = recommendationService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Добавление новой рекомендации", description = "Добавление новой рекомендации")
    @PostMapping("/")
    public ResponseEntity<RecommendationDTO> saveRecommendation(@RequestBody RecommendationDTO dto) {
        RecommendationDTO savedDTO = recommendationService.saveRecommendation(dto);
        return new ResponseEntity<>(savedDTO, HttpStatus.OK);
    }

    @Operation(summary = "Редактирование рекомендации", description = "Редактирование рекомендации")
    @PutMapping("/{id}")
    public ResponseEntity<RecommendationDTO> editRecommendation(@PathVariable Long id,
                                                                @RequestBody RecommendationDTO dto) {
        RecommendationDTO updatedDTO = recommendationService.editRecommendation(id, dto);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }
}
