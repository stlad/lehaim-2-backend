package ru.vaganov.lehaim.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.lehaim.models.genes.DiagnosisGene;
import ru.vaganov.lehaim.models.genes.Gene;

import java.util.List;

@Data
@NoArgsConstructor
@Entity @Table(name = "diagnoses_catalog")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "diagnosis")
    private List<DiagnosisGene> genes;
}
