package ru.vaganov.ResourceServer.repositories.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.dictionary.recommendation.RegenerationParameterRanges;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.recommendations.RegenerationChartState;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegenerationChartStateRepository extends JpaRepository<RegenerationChartState, UUID> {
    @Query("SELECT r FROM RegenerationChartState r " +
            "WHERE r.diagnosis.id = :#{#diagnosis.id} " +
            "AND r.rangeNeuLymf = :#{#neuLymf} " +
            "AND r.rangeNeuMon = :#{#neuMon} " +
            "AND r.rangeLymfMon = :#{#lymfMon}")
    Optional<RegenerationChartState> findState(@Param("diagnosis") Diagnosis diagnosis,
                                               @Param("neuLymf") RegenerationParameterRanges.NEU_LYMF neuLymf,
                                               @Param("neuMon") RegenerationParameterRanges.NEU_MON neuMon,
                                               @Param("lymfMon") RegenerationParameterRanges.LYMF_MON lymfMon);
}
