package ru.vaganov.ResourceServer.models.recommendations;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.ResourceServer.dictionary.recommendation.RegenerationParameterRanges;
import ru.vaganov.ResourceServer.models.Diagnosis;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "regeneration_chart_states")
public class RegenerationChartState {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "range_NEU_LYMF")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.NEU_LYMF rangeNeuLymf;

    @Column(name = "range_NEU_MON")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.NEU_MON rangeNeuMon;

    @Column(name = "range_LYMF_MON")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.LYMF_MON rangeLymfMon;

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
    public enum Axis {
        NeuLymf(4L, 2L),
        NeuMon(4L, 3L),
        LymfMon(2L, 3L);

        private final Long firstParamId;
        private final Long secondParamId;
    }
}
