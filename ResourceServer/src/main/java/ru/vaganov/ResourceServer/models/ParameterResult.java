package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class ParameterResult {

    @Id
    @GeneratedValue
    private Long id;
    private Double value;

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private Parameter parameter;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private OncologicalTest attachedTest;

    public boolean isValid(){
        if(parameter == null) return false;
        return parameter.getRefMin() <= value && value <= parameter.getRefMax();
    }

}
