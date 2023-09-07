package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Patient {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastname;
    private String patronymic;

    private LocalDate birthdate;
    private boolean alive;
    private String mainDiagnosis;
    private String otherDiagnosis;

    private String info;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public enum Gender{
        Male, Female
    }
}
