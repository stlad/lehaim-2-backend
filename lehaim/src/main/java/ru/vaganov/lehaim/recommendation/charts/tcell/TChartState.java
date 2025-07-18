package ru.vaganov.lehaim.recommendation.charts.tcell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;
import ru.vaganov.lehaim.recommendation.Recommendation;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "t_tcell_chart_state")
public class TChartState {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "cd4_compare_cd8")
    private Integer cd4compareCd8;

    @Column(name = "range_neu_lymf")
    @Enumerated(EnumType.STRING)
    private TParameterRanges.NEU_LYMF rangeNeuLymf;

    @Column(name = "range_neu_cd3")
    @Enumerated(EnumType.STRING)
    private TParameterRanges.NEU_CD3 rangeNeuCd3;

    @Column(name = "range_neu_cd4")
    @Enumerated(EnumType.STRING)
    private TParameterRanges.NEU_CD4 rangeNeuCd4;

    @Column(name = "range_neu_cd8")
    @Enumerated(EnumType.STRING)
    private TParameterRanges.NEU_CD8 rangeNeuCd8;

    @Column(name = "range_cd4")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TParameterRanges.CD4 rangeCd4 = TParameterRanges.CD4.EMPTY;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_diagnosis", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_recommendation", referencedColumnName = "id")
    private Recommendation recommendation;
}
