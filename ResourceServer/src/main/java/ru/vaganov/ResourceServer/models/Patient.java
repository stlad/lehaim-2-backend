package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String patronymic;

    private LocalDate birthdate;
    private LocalDate deathdate;

    private String t;
    private String n;
    private String m;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id")
    private Diagnosis diagnosis;

    private String info;
    private String operationComments;
    private String diagnosisComments;
    private String chemotherapyComments;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public enum Gender{
        Male, Female
    }



    @Override
    public String toString(){
        return String.format("(%d) %s %s", getId(), getLastname(), getName());
    }
}
