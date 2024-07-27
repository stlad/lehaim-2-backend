package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;

import java.util.UUID;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {
}
