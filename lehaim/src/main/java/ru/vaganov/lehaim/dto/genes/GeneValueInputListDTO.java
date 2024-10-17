package ru.vaganov.lehaim.dto.genes;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Значения генов")
public class GeneValueInputListDTO {

    @Schema(description = "Значения")
    private List<GeneValueInputDTO> values;
}
