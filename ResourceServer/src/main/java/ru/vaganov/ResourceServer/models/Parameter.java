package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "catalog", uniqueConstraints={@UniqueConstraint(columnNames={"name", "additionalName"})})
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Parameter {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String additionalName;
    private String unit;
    private Double refMin;
    private Double refMax;

    @Enumerated(value = EnumType.STRING)
    private ResearchType researchType;

    public enum ResearchType{
        Hematological, immunological, Cytokine, None
    }

    @Override
    public String toString(){
        return name + " (" + additionalName + ")";
    }

}
