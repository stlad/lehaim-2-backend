package ru.vaganov.ResourceServer.models.recommendations;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.ResourceServer.dictionary.recommendation.CytokineParameterRange;
import ru.vaganov.ResourceServer.models.Diagnosis;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cytokine_chart_states")
public class CytokineChartState {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "range_TNFa")
    @Enumerated(EnumType.STRING)
    private CytokineParameterRange rangeTNFa;

    @Column(name = "range_IFNy")
    @Enumerated(EnumType.STRING)
    private CytokineParameterRange rangeIFNy;

    @Column(name = "range_IL2")
    @Enumerated(EnumType.STRING)
    private CytokineParameterRange rangeIL2;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "diagnosis_Id", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommendation_Id", referencedColumnName = "id")
    private Recommendation recommendation;

    @Getter
    @AllArgsConstructor
    public enum Axis{
        TNFa(38L,39L),
        IFNy(36L,37L),
        IL2(40L,41L);

        private Long firstParamId;
        private Long secondParamId;
    }
}
