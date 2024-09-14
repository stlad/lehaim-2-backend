package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.vaganov.lehaim.models.Parameter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(title = "ParameterDTO: Параметр, используемый в обследовании")
public class ParameterDTO {

    @Schema(title = "Идентификатор параметра из каталога")
    private Long id;

    @Schema(title = "Название параметра из каталога")
    private String name;

    @Schema(title = "Доп. часть названия параметра из каталога")
    private String additionalName;

    @Schema(title = "Единица измерения")
    private String unit;

    @Schema(title = "Нижнее значение референтного значения нормы")
    private Double refMin;

    @Schema(title = "Верхнее значение референтного значения нормы")
    private Double refMax;

    @Schema(title = "Тип исследования для данного параметра")
    private Parameter.ResearchType researchType;

    @Schema(title = "Используется ли в системе, флаг")
    private Boolean isActive;


    @Override
    public String toString(){
        return String.format("%s (%s) [%.2f : %.2f]",name,additionalName,refMax,refMax);
    }
}
