package ru.vaganov.lehaim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiagnosisDTO {
    private Integer id;
    private String code;
    private String description;
}

