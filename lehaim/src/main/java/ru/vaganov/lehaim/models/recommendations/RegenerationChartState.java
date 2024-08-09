package ru.vaganov.lehaim.models.recommendations;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.dictionary.recommendation.ParameterChartAxis;
import ru.vaganov.lehaim.dictionary.recommendation.RegenerationParameterRanges;
import ru.vaganov.lehaim.models.Diagnosis;

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

    @Column(name = "range_neu_lymf")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.NEU_LYMF rangeNeuLymf;

    @Column(name = "range_neu_mon")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.NEU_MON rangeNeuMon;

    @Column(name = "range_lymf_mon")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.LYMF_MON rangeLymfMon;

    @Column(name = "range_neu")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.NEUTROPHILS rangeNeu;

    @Column(name = "range_leu")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.LEUKOCYTES rangeLeu;

    @Column(name = "range_plt")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.PLATELETS rangePlt;

    @Column(name = "range_hgb")
    @Enumerated(EnumType.STRING)
    private RegenerationParameterRanges.HEMOGLOBIN rangeHgb;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "diagnosis_Id", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommendation_id", referencedColumnName = "id")
    private Recommendation recommendation;

    @Getter
    @AllArgsConstructor
    public enum Axis implements ParameterChartAxis {
        NeuLymf(4L, 2L),
        NeuMon(4L, 3L),
        LymfMon(2L, 3L),
        Hemoglobin(7L, 7L),
        Leukocytes(1L,1L),
        Neutrophils(4L,4L),
        Platelets(8L,8L);

        private final Long firstParamId;
        private final Long secondParamId;
    }
}
