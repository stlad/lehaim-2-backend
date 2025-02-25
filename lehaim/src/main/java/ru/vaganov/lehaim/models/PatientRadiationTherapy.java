package ru.vaganov.lehaim.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_patient_radiation_therapy")
public class PatientRadiationTherapy {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private LocalDate startTherapy;
    private LocalDate endTherapy;
    private String comment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_patient", referencedColumnName = "id")
    private Patient patient;

    public boolean isDateInTherapy(LocalDate date){
        if(startTherapy == null || endTherapy == null){
            return false;
        }
        return startTherapy.isBefore(date) && endTherapy.isAfter(date);
    }
}
