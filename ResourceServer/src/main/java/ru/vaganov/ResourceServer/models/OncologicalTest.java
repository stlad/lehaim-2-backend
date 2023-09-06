package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table(name = "oncological_tests")
@Entity
public class OncologicalTest {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate testDate;

    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    private Patient patientOwner;

}
