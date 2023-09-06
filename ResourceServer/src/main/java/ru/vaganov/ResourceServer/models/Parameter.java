package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

@Data
@Table(name = "catalog")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parameter {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String name;

    @Column(unique=true)
    private String abbreviation;
    private String unit;
    private Double refMin;
    private Double refMax;

    @Enumerated(value = EnumType.STRING)
    private ResearchType researchType;



    public enum ResearchType{
        Hematological, immunological, Cytokine, None
    }

}
