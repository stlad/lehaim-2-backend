package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.vaganov.ResourceServer.dto.recommendation.RecommendationDTO;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationDTO toDTO(Recommendation entity);

    Recommendation fromDTO(RecommendationDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(RecommendationDTO dto, @MappingTarget Recommendation entity);
}