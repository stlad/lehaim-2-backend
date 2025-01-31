package ru.vaganov.lehaim.repositories.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.lehaim.models.recommendations.TChartState;

import java.util.UUID;

public interface TChartStateRepository extends JpaRepository<TChartState, UUID> {
}
