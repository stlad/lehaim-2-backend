package ru.vaganov.lehaim.gene;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.gene.dto.GeneDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueInputListDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueOutputDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueOutputListDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genes")
@Tag(name = "Гены")
@Slf4j
@RequiredArgsConstructor
public class GeneController {
    private final GenesService genesService;

    @Operation(summary = "Получить все гены для диагноза")
    @GetMapping("/diagnosis/{diagnosisId}")
    public ResponseEntity<List<GeneDTO>> findGenesByDiagnosis(@PathVariable Integer diagnosisId) {
        return new ResponseEntity<>(genesService.getGenesByDiagnosis(diagnosisId), HttpStatus.OK);
    }

    @Operation(summary = "Получить каталог генов")
    @GetMapping("/all")
    public ResponseEntity<List<GeneDTO>> getGeneCatalog() {
        return new ResponseEntity<>(genesService.getGeneCatalog(), HttpStatus.OK);
    }

    @Operation(summary = "Сохранить значения генов")
    @PostMapping("/{patientId}")
    public ResponseEntity<GeneValueOutputListDTO> saveNewGeneValues(@PathVariable UUID patientId,
                                                                    @RequestBody GeneValueInputListDTO dto) {
        return new ResponseEntity<>(genesService.saveGeneValues(patientId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Обновить значения генов")
    @PutMapping("/{patientId}")
    public ResponseEntity<GeneValueOutputListDTO> updateGeneValues(@PathVariable UUID patientId,
                                                                   @RequestBody GeneValueInputListDTO dto) {
        return new ResponseEntity<>(genesService.updateGeneValues(patientId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Получить значения генов для пациента")
    @GetMapping("/{patientId}")
    public ResponseEntity<Map<Integer, List<GeneValueOutputDTO>>> updateGeneValues(@PathVariable UUID patientId) {
        return new ResponseEntity<>(genesService.getGeneValuesForPatient(patientId), HttpStatus.OK);
    }
}
