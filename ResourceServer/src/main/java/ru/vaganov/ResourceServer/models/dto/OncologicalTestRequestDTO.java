package ru.vaganov.ResourceServer.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OncologicalTestRequestDTO{

    private LocalDate testDate;

    @NotNull(message = "results must not be null")
    private List<ParameterResultRequestDTO> results;
}

