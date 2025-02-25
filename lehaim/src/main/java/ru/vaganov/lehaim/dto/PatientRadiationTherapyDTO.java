package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Информация о лучевой терапии")
public class PatientRadiationTherapyDTO {

    @Schema(description = "Дата начала терапии")
    private LocalDate startTherapy;

    @Schema(description = "Дата окончания терапии")
    private LocalDate endTherapy;

    @Schema(description = "Комментарий")
    private String comment;
}
