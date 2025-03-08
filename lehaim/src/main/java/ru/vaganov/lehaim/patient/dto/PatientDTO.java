package ru.vaganov.lehaim.patient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.vaganov.lehaim.dictionary.Gender;

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

    @Schema(description = "Имя", example = "Иван")
    private String name;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastname;

    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;

    @Schema(description = "Дата рождения", example = "1970-01-01")
    private String birthdate;

    @Schema(description = "Дата смерти", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2020-01-01")
    private String deathdate;

    @Schema(description = "Идентификатор анализа из каталога", example = "1")
    private Integer diagnosisId;

    @Schema(description = "Дополнительная информация")
    private String info;

    @Schema(description = "Пол", example = "Male")
    private Gender gender;

    @Schema(description = "Комментарии к операции")
    private String operationComments;

    @Schema(description = "Коментарии к диагнозу")
    private String diagnosisComments;

    @Schema(description = "Комментарии к химиотерапии")
    private String chemotherapyComments;

    @Schema(description = "Дата операции", example = "2015-01-01")
    private String operationDate;

    @Schema(description = "Дополнительный диагноз. Для С50")
    private String additionalDiagnosis;


    private String t;
    private String n;
    private String m;
    private String g;

    @Schema(description = "Информация о лучевой терапии")
    private PatientRadiationTherapyDTO radiationTherapy;
}
