package ru.vaganov.lehaim.models.recommendations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.dictionary.recommendation.SIParameterRanges;
import ru.vaganov.lehaim.models.Diagnosis;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "systemic_inflammation_chart_states")
public class SIChartState {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "range_piv")
    @Enumerated(EnumType.STRING)
    private SIParameterRanges.Piv rangePiv;

    @Column(name = "range_siri")
    @Enumerated(EnumType.STRING)
    private SIParameterRanges.Siri rangeSiri;

    @Column(name = "range_neu_density")
    @Enumerated(EnumType.STRING)
    private SIParameterRanges.NeuDensity rangeNeuDensity;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "diagnosis_Id", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommendation_id", referencedColumnName = "id")
    private Recommendation recommendation;
}
