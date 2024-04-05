package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class ParameterResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private Parameter parameter;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private OncologicalTest attachedTest;

    public boolean isValid(){
        if(parameter == null) return false;
        return parameter.getRefMin() <= value && value <= parameter.getRefMax();
    }

}
