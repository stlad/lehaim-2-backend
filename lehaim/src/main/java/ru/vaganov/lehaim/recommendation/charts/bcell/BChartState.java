package ru.vaganov.lehaim.recommendation.charts.bcell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.recommendation.Recommendation;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_bcell_chart_state")
@Builder
public class BChartState {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "range_iga")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.IgA rangeIga = BParameterRanges.IgA.EMPTY;

    @Column(name = "range_igg")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.IgG rangeIgg = BParameterRanges.IgG.EMPTY;

    @Column(name = "range_cd19")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.CD19 rangeCd19 = BParameterRanges.CD19.EMPTY;

    @Column(name = "range_cd19_tnk")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.CD19_TNK rangeCd19Tnk = BParameterRanges.CD19_TNK.EMPTY;


    @Column(name = "range_neu_lymf")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.NEU_LYMF rangeNeuLymf = BParameterRanges.NEU_LYMF.EMPTY;

    @Column(name = "range_neu_cd19")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.NEU_CD19 rangeNeuCd19 = BParameterRanges.NEU_CD19.EMPTY;

    @Column(name = "range_neu_cd4")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.NEU_CD4 rangeNeuCd4 = BParameterRanges.NEU_CD4.EMPTY;

    @Column(name = "range_cd19_cd4")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BParameterRanges.CD19_CD4 rangeCd19Cd4 = BParameterRanges.CD19_CD4.EMPTY;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_diagnosis", referencedColumnName = "id")
    private Diagnosis diagnosis;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_recommendation", referencedColumnName = "id")
    private Recommendation recommendation;
}
