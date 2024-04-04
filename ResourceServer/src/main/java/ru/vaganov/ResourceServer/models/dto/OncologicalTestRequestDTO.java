package ru.vaganov.ResourceServer.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OncologicalTestRequestDTO{

    private LocalDate testDate;
    private List<ParameterResultRequestDTO> results;
}

