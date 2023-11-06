package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.recommendations.IntervalRecommendation;

@Repository
public interface IntervalRecRepo extends JpaRepository<IntervalRecommendation, Long> {
    public IntervalRecommendation findByParameter(Parameter parameter);
}
