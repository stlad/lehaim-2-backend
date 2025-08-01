package ru.vaganov.lehaim.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.dictionary.Gender;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_patient")
public class Patient {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private String lastname;
    private String patronymic;

    private LocalDate birthdate;
    private LocalDate deathdate;

    private String t;
    private String n;
    private String m;
    private String g;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "link_diagnosis", referencedColumnName = "id")
    private Diagnosis diagnosis;
    private String additionalDiagnosis;

    private String info;
    private String operationComments;
    private String diagnosisComments;
    private String chemotherapyComments;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private LocalDate operationDate;

    @OneToOne(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientRadiationTherapy radiationTherapy;

    public void setRadiationTherapy(PatientRadiationTherapy radiationTherapy){
        this.radiationTherapy = radiationTherapy;
        radiationTherapy.setPatient(this);
    }

    @Override
    public String toString() {
        return String.format("(%s) %s %s", getId(), getLastname(), getName());
    }
}
