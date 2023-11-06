package ru.vaganov.ResourceServer.models.recommendations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.vaganov.ResourceServer.models.Parameter;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class IntervalRecommendation {


    @Id
    @GeneratedValue
    private Long id;
    private Double value;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private Parameter parameter;

    private String resultIfGreater;
    private String resultIfLess;
    private String resultIfInside;

}
