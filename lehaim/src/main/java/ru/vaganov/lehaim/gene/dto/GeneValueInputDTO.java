package ru.vaganov.lehaim.gene.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Значение генов для пациента")
public class GeneValueInputDTO {
    @Schema(description = "ИД гена из каталога")
    private Long geneId;

    @Schema(description = "ИД диагноза из каталога")
    private Integer diagnosisId;

    @Schema(description = "Значение гена")
    private String geneValue;
}
