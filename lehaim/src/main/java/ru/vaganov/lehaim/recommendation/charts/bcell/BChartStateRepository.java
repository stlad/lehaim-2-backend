package ru.vaganov.lehaim.recommendation.charts.bcell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.recommendation.charts.tcell.TChartState;

import java.util.Optional;
import java.util.UUID;

public interface BChartStateRepository extends JpaRepository<BChartState, UUID> {

    @Query("SELECT c FROM BChartState c " +
            "WHERE c.diagnosis.id = :#{#diagnosis.id} " +
            "AND c.rangeCd19 = :#{#state.rangeCd19} " +
            "AND c.rangeCd19Tnk = :#{#state.rangeCd19Tnk} " +
            "AND c.rangeIga = :#{#state.rangeIga} " +
            "AND c.rangeIgg = :#{#state.rangeIgg} " +
            "AND c.rangeNeuLymf = :#{#state.rangeNeuLymf} " +
            "AND c.rangeNeuCd19 = :#{#state.rangeNeuCd19} " +
            "AND c.rangeNeuCd4 = :#{#state.rangeNeuCd4} " +
            "AND c.rangeCd19Cd4 = :#{#state.rangeCd19Cd4} ")
    Optional<BChartState> findState(@Param("diagnosis") Diagnosis diagnosis, @Param("state") BChartState state);
}
