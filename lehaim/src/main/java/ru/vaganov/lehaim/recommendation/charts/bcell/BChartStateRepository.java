package ru.vaganov.lehaim.recommendation.charts.bcell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.recommendation.charts.tcell.TChartState;

import java.util.Optional;
import java.util.UUID;

public interface BChartStateRepository extends JpaRepository<TChartState, UUID> {

    @Query("SELECT c FROM TChartState c " +
            "WHERE c.diagnosis.id = :#{#diagnosis.id} " +
            "AND c.cd4compareCd8 = :#{#state.cd4compareCd8} " +
            "AND c.rangeCd4 = :#{#state.rangeCd4} " +
            "AND c.rangeNeuLymf = :#{#state.rangeNeuLymf} " +
            "AND c.rangeNeuCd3 = :#{#state.rangeNeuCd3} " +
            "AND c.rangeNeuCd4 = :#{#state.rangeNeuCd4} " +
            "AND c.rangeNeuCd8 = :#{#state.rangeNeuCd8} ")
    Optional<TChartState> findState(@Param("diagnosis") Diagnosis diagnosis, @Param("state") TChartState state);
}
