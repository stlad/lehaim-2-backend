package ru.vaganov.lehaim.recommendation.charts.si;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.Diagnosis;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SIChartStateRepository extends JpaRepository<SIChartState, UUID> {
    @Query("SELECT c FROM SIChartState c " +
            "WHERE c.diagnosis.id = :#{#diagnosis.id} " +
            "AND c.rangeSiri = :#{#siri} " +
            "AND c.rangePiv = :#{#piv} " +
            "AND c.rangeNeuDensity = :#{#neu}")
    Optional<SIChartState> findState(@Param("diagnosis") Diagnosis diagnosis,
                                     @Param("siri") SIParameterRanges.Siri siri,
                                     @Param("piv") SIParameterRanges.Piv piv,
                                     @Param("neu") SIParameterRanges.NeuDensity neuDensity);
}
