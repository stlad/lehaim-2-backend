package ru.vaganov.ResourceServer.models.dto;

import lombok.*;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class PatientDTO {
    private Long id;
    private String name;
    private String lastname;
    private String patronymic;
    private LocalDate birthdate;
    private LocalDate deathdate;
    private String mainDiagnosis;
    private String otherDiagnosis;
    private String info;
    private Patient.Gender gender;
    private String operationComments;
    private String diagnosisComments;
    private String chemotherapyComments;
}
