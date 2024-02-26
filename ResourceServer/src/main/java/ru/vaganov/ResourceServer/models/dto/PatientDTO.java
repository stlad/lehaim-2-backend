package ru.vaganov.ResourceServer.models.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Boolean alive;
    private String mainDiagnosis;
    private String otherDiagnosis;
    private String info;
    private Patient.Gender gender;
}
