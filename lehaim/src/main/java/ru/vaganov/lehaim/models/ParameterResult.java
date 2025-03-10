package ru.vaganov.lehaim.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_parameter_result")
public class ParameterResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_parameter", referencedColumnName = "id")
    private Parameter parameter;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_oncological_test", referencedColumnName = "id")
    private OncologicalTest attachedTest;

    public boolean isValid() {
        if (parameter == null) return false;
        return parameter.getRefMin() <= value && value <= parameter.getRefMax();
    }

}
