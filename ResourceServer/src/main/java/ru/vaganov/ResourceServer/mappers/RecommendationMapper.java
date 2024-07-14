package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vaganov.ResourceServer.models.Recommendation;
import ru.vaganov.ResourceServer.models.dto.RecommendationDTO;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class})
public interface RecommendationMapper {

    @Mapping(target = "firstParamId", source = "entity.firstParam.id")
    @Mapping(target = "secondParamId", source = "entity.secondParam.id")
    RecommendationDTO toDTO(Recommendation entity);
    Recommendation fromDTO(RecommendationDTO dto);

}
