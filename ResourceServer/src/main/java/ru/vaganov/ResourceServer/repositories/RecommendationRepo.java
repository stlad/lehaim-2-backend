package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Recommendation;

@Repository
public interface RecommendationRepo extends JpaRepository<Recommendation, Long> {
}
