package ru.vaganov.lehaim.recommendation.charts.cytokine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CytokineChartStateRepository extends JpaRepository<CytokineChartState, UUID> {
    @Query("SELECT c FROM CytokineChartState c " +
            "WHERE c.diagnosis.id = :#{#diagnosis.id} " +
            "AND c.rangeIFNy = :#{#ifny} " +
            "AND c.rangeTNFa = :#{#tnfa} " +
            "AND c.rangeIL2 = :#{#il2}")
    Optional<CytokineChartState> findState(@Param("diagnosis") Diagnosis diagnosis,
                                           @Param("tnfa") CytokineParameterRange tnfa,
                                           @Param("ifny") CytokineParameterRange ifny,
                                           @Param("il2") CytokineParameterRange il2);
}
