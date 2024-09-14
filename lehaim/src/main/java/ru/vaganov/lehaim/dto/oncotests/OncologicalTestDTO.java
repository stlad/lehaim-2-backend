package ru.vaganov.lehaim.dto.oncotests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(title = "OncologicalTestDTO: Общая информация об обследовании")
public class OncologicalTestDTO {

    @Schema(title = "Индентификатор")
    private Long id;

    @Schema(title = "Дата обследования")
    private LocalDate testDate;
}
