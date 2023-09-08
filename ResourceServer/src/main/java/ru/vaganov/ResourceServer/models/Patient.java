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
    private LocalDate deathdate;
    private Boolean alive;
    private String mainDiagnosis;
    private String otherDiagnosis;

    private String info;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public enum Gender{
        Male, Female
    }

    public void updateFieldsBy(Patient changes){
        if(changes.getName()!=null) setName(changes.getName());
        if(changes.getLastname()!=null) setLastname(changes.getLastname());
        if(changes.getPatronymic()!=null) setPatronymic(changes.getPatronymic());
        if(changes.getBirthdate()!=null) setBirthdate(changes.getBirthdate());
        if(changes.getDeathdate()!=null) setDeathdate(changes.getDeathdate());
        if(changes.getAlive()!=null) setAlive(changes.getAlive());
        if(changes.getMainDiagnosis()!=null) setMainDiagnosis(changes.getMainDiagnosis());
        if(changes.getOtherDiagnosis()!=null) setOtherDiagnosis(changes.getOtherDiagnosis());
        if(changes.getInfo()!=null) setInfo(changes.getInfo());
        if(changes.getGender()!=null) setGender(changes.getGender());
    }
}
