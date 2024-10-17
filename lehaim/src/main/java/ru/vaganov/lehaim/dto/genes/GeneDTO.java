package ru.vaganov.lehaim.dto.genes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Ген")
public class GeneDTO {

    @Schema(description = "Идентификстор гена из каталога")
    private Long id;

    @Schema(description = "Название гена из каталога")
    private String geneName;

    @Schema(description = "Список возможных значений")
    private String[] possibleValues;

}
