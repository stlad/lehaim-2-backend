package ru.vaganov.lehaim.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.vaganov.lehaim.dto.recommendation.RecommendationDTO;
import ru.vaganov.lehaim.models.recommendations.Recommendation;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationDTO toDTO(Recommendation entity);

    Recommendation fromDTO(RecommendationDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(RecommendationDTO dto, @MappingTarget Recommendation entity);
}
