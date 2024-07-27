package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.ResourceServer.dto.recommendation.RecommendationDTO;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationDTO toDTO(Recommendation entity);

    Recommendation fromDTO(RecommendationDTO dto);
}
