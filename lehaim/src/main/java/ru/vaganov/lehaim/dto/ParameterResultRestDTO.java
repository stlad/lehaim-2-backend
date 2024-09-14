package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(title = "ParameterResultRestDTO: Результат обследования по параметру, краткая форма")
public class ParameterResultRestDTO {
    @Schema(title = "Идентификатор параметра в каталоге")
    private Long catalogId;

    @Schema(title = "Результат обследования для параметра")
    private Double value;
}
