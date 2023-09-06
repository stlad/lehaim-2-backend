package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public enum Gender{
        Male, Female
    }
}
