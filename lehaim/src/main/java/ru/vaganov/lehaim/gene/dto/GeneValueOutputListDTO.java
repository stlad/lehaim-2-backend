package ru.vaganov.lehaim.gene.dto;


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
public class GeneValueOutputListDTO {

    @Schema(description = "ID пациента")
    private UUID patientId;

    @Schema(description = "Значения")
    private List<GeneValueOutputDTO> values;
}
