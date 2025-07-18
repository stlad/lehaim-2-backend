package ru.vaganov.lehaim.catalog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.lehaim.gene.entity.DiagnosisGene;

import java.util.List;

@Data
@NoArgsConstructor
@Entity @Table(name = "t_list_diagnosis")
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
