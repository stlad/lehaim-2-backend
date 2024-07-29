package ru.vaganov.ResourceServer.dto;

import lombok.*;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class PatientDTO {
    private UUID id;
    private String name;
    private String lastname;
    private String patronymic;
    private LocalDate birthdate;
    private LocalDate deathdate;

    private Integer diagnosisId;
    private String info;
    private Patient.Gender gender;
    private String operationComments;
    private String diagnosisComments;
    private String chemotherapyComments;


    private String t;
    private String n;
    private String m;
}
