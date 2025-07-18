package ru.vaganov.lehaim.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.vaganov.lehaim.catalog.entity.Parameter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Параметр, используемый в обследовании")
public class ParameterDTO {

    @Schema(description = "Идентификатор параметра из каталога")
    private Long id;

    @Schema(description = "Название параметра из каталога")
    private String name;

    @Schema(description = "Доп. часть названия параметра из каталога")
    private String additionalName;

    @Schema(description = "Единица измерения")
    private String unit;

    @Schema(description = "Нижнее значение референтного значения нормы")
    private Double refMin;

    @Schema(description = "Верхнее значение референтного значения нормы")
    private Double refMax;

    @Schema(description = "Тип исследования для данного параметра")
    private Parameter.ResearchType researchType;

    @Schema(description = "Используется ли в системе, флаг")
    private Boolean isActive;


    @Override
    public String toString(){
        return String.format("%s (%s) [%.2f : %.2f]",name,additionalName,refMax,refMax);
    }
}
