package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Результат обследования по параметру, полная форма")
public class ParameterResultDTO {
    @Schema(description = "Идентификатор результата")
    private Long id;

    @Schema(description = "Результат обследования для параметра")
    private Double value;

    @Schema(description = "Связанный параметр из каталога")
    private ParameterDTO parameter;
}
