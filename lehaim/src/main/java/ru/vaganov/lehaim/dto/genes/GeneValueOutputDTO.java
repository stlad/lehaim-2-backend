package ru.vaganov.lehaim.dto.genes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Значение генов для пациента (Исходящее)")
public class GeneValueOutputDTO {

    @Schema(description = "ИД записи")
    private Long id;

    @Schema(description = "ИД гена из каталога")
    private Long geneId;

    @Schema(description = "ИД диагноза из каталога")
    private Integer diagnosisId;

    @Schema(description = "Значение гена")
    private String geneValue;
}
