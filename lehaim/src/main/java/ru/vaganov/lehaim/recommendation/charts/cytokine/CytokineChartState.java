package ru.vaganov.lehaim.recommendation.charts.cytokine;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.recommendation.utils.ParameterChartAxis;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.recommendation.Recommendation;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "t_cytokine_chart_state")
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
    @JoinColumn(name = "link_diagnosis", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_recommendation", referencedColumnName = "id")
    private Recommendation recommendation;

    @Getter
    @AllArgsConstructor
    public enum Axis implements ParameterChartAxis {
        TNFa(38L,39L),
        IFNy(36L,37L),
        IL2(40L,41L);

        private final Long firstParamId;
        private final Long secondParamId;
    }
}
