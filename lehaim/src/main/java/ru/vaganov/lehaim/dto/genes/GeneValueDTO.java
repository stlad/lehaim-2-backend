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
@Schema(description = "Значение генов для пациента")
public class GeneValueDTO {

    private Long id;

    private UUID patientId;

    private Long geneId;

    private Integer diagnosisId;

    private String geneValue;
}
