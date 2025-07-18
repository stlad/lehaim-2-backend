package ru.vaganov.lehaim.oncotest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(description = "Результат обследования по параметру, краткая форма")
public class ParameterResultRestDTO {
    @Schema(description = "Идентификатор параметра в каталоге")
    private Long catalogId;

    @Schema(description = "Результат обследования для параметра")
    private Double value;
}
