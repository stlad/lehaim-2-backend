package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(title = "ParameterResultDTO: Результат обследования по параметру, полная форма")
public class ParameterResultDTO {
    @Schema(title = "Идентификатор результата")
    private Long id;

    @Schema(title = "Результат обследования для параметра")
    private Double value;

    @Schema(title = "Связанный параметр из каталога")
    private ParameterDTO parameter;
}
