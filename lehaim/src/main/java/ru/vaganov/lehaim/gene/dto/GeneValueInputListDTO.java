package ru.vaganov.lehaim.gene.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Значения генов")
public class GeneValueInputListDTO {

    @Schema(description = "Значения")
    private List<GeneValueInputDTO> values;
}
