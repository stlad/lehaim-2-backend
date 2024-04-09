package ru.vaganov.ResourceServer.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data @Builder
public class OncologicalTestRestDTO {
    private Long id;
    private LocalDate testDate;

    @NotNull(message = "results must not be null")
    private List<ParameterResultRestDTO> results;
}

