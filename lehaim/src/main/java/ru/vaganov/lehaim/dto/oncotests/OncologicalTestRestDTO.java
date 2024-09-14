package ru.vaganov.lehaim.dto.oncotests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Schema(
        title = "OncologicalTestRestDTO: Расширенная информация об обследовании",
        description = "Полная информация об обслоедовании, включает в себя результаты анализов"
)
public class OncologicalTestRestDTO {

    @Schema(title = "Индентификатор")
    private Long id;
    @Schema(title = "Дата обследования")
    private LocalDate testDate;

    @NotNull(message = "results must not be null")
    private List<ParameterResultRestDTO> results;
}

