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
@Schema(title = "PatientDTO: Вся информация о пациенте")
public class PatientDTO {

    @Schema(title = "Идентификатор")
    private UUID id;

    @Schema(title = "Имя ")
    private String name;

    @Schema(title = "Фамилия ")
    private String lastname;

    @Schema(title = "Отчество ")
    private String patronymic;

    @Schema(title = "Дата рождения ")
    private LocalDate birthdate;

    @Schema(title = "Дата смерти ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate deathdate;

    @Schema(title = "Идентификатор анализа из каталога")
    private Integer diagnosisId;

    @Schema(title = "Дополнительная информация")
    private String info;

    @Schema(title = "Пол")
    private Gender gender;

    @Schema(title = "Комментарии к операции")
    private String operationComments;

    @Schema(title = "Коментарии к диагнозу")
    private String diagnosisComments;

    @Schema(title = "Комментарии к химиотерапии")
    private String chemotherapyComments;

    @Schema(title = "Дата операции")
    private LocalDate operationDate;


    private String t;
    private String n;
    private String m;
}
