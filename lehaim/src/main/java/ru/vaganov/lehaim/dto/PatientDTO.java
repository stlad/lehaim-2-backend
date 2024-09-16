package ru.vaganov.lehaim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.vaganov.lehaim.dictionary.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Вся информация о пациенте")
public class PatientDTO {

    @Schema(description = "Идентификатор")
    private UUID id;

    @Schema(description = "Имя ")
    private String name;

    @Schema(description = "Фамилия ")
    private String lastname;

    @Schema(description = "Отчество ")
    private String patronymic;

    @Schema(description = "Дата рождения ")
    private LocalDate birthdate;

    @Schema(description = "Дата смерти ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate deathdate;

    @Schema(description = "Идентификатор анализа из каталога")
    private Integer diagnosisId;

    @Schema(description = "Дополнительная информация")
    private String info;

    @Schema(description = "Пол")
    private Gender gender;

    @Schema(description = "Комментарии к операции")
    private String operationComments;

    @Schema(description = "Коментарии к диагнозу")
    private String diagnosisComments;

    @Schema(description = "Комментарии к химиотерапии")
    private String chemotherapyComments;

    @Schema(description = "Дата операции")
    private LocalDate operationDate;


    private String t;
    private String n;
    private String m;
}
