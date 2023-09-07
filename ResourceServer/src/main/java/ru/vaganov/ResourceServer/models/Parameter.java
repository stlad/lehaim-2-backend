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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String additionalName;
    private String unit;
    private Double refMin;
    private Double refMax;

    @Enumerated(value = EnumType.STRING)
    private ResearchType researchType;

    public enum ResearchType{
        Hematological, Immunological, Cytokine, None
    }

    @Override
    public String toString(){
        return String.format("%s (%s) [%.2f : %.2f]",name,additionalName,refMax,refMax);
    }

    public void updateFieldsBy(Parameter changes){
        if(changes.getName()!=null) setName(changes.getName());
        if(changes.getAdditionalName()!=null) setAdditionalName(changes.getAdditionalName());
        if(changes.getUnit()!=null) setUnit(changes.getUnit());
        if(changes.getRefMin()!=null) setRefMin(changes.getRefMin());
        if(changes.getRefMax()!=null) setRefMax(changes.getRefMax());
        if(changes.getResearchType()!=null) setResearchType(changes.getResearchType());
    }

}
