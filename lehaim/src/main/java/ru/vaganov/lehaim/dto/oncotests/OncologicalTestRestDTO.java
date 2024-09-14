package ru.vaganov.lehaim.dto.oncotests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;

import java.time.LocalDate;
import java.util.List;

@Data @Builder
public class OncologicalTestRestDTO {
    private Long id;
    private LocalDate testDate;

    @NotNull(message = "results must not be null")
    private List<ParameterResultRestDTO> results;
}

