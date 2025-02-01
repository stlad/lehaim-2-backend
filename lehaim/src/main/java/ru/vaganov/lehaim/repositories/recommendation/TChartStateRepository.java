package ru.vaganov.lehaim.repositories.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vaganov.lehaim.dictionary.recommendation.TParameterRanges;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.models.recommendations.TChartState;

import java.util.Optional;
import java.util.UUID;

public interface TChartStateRepository extends JpaRepository<TChartState, UUID> {

    @Query("SELECT c FROM TChartState c " +
            "WHERE c.diagnosis.id = :#{#diagnosis.id} " +
            "AND c.cd4compareCd8 = :#{#cd4compareCd8} " +
            "AND c.rangeCd4 = :#{#rangeCd4} " +
            "AND c.rangeNeuLymf = :#{#rangeNeuLymf}")
    Optional<TChartState> findState(@Param("diagnosis") Diagnosis diagnosis,
                                           @Param("cd4compareCd8")  Integer cd4compareCd8,
                                           @Param("rangeCd4")  TParameterRanges.CD4 rangeCd4,
                                           @Param("rangeNeuLymf") TParameterRanges.NEU_LYMF rangeNeuLymf);

}
