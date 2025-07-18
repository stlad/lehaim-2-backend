package ru.vaganov.lehaim.recommendation.charts.regeneration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegenerationChartStateRepository extends JpaRepository<RegenerationChartState, UUID> {
    @Query("SELECT r FROM RegenerationChartState r " +
            "WHERE r.diagnosis.id = :#{#diagnosis.id} " +
            "AND r.rangeNeuLymf = :#{#neuLymf} " +
            "AND r.rangeNeuMon = :#{#neuMon} " +
            "AND r.rangeLymfMon = :#{#lymfMon} " +
            "AND r.rangeNeu = :#{#neu} " +
            "AND r.rangeLeu = :#{#leu} " +
            "AND r.rangePlt = :#{#plt} " +
            "AND r.rangeHgb = :#{#hgb}")
    Optional<RegenerationChartState> findState(@Param("diagnosis") Diagnosis diagnosis,
                                               @Param("neuLymf") RegenerationParameterRanges.NEU_LYMF neuLymf,
                                               @Param("neuMon") RegenerationParameterRanges.NEU_MON neuMon,
                                               @Param("lymfMon") RegenerationParameterRanges.LYMF_MON lymfMon,
                                               @Param("neu") RegenerationParameterRanges.NEUTROPHILS neu,
                                               @Param("leu") RegenerationParameterRanges.LEUKOCYTES leu,
                                               @Param("plt") RegenerationParameterRanges.PLATELETS plt,
                                               @Param("hgb") RegenerationParameterRanges.HEMOGLOBIN hgb);
}
