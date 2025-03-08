package ru.vaganov.lehaim.patient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Информация о лучевой терапии")
public class PatientRadiationTherapyDTO {

    @Schema(description = "Дата начала терапии", example = "2019-01-01")
    private String startTherapy;

    @Schema(description = "Дата окончания терапии", example = "2020-01-01")
    private String endTherapy;

    @Schema(description = "Комментарий", example = "Комментарий")
    private String comment;
}
