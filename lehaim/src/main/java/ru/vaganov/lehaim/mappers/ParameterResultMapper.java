package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class, OncologicalTestMapper.class})
public interface ParameterResultMapper {

    ParameterResult fromDto(ParameterResultDTO dto);
    ParameterResultDTO toDto(ParameterResult entity);
    List<ParameterResultDTO> toDto(List<ParameterResult> entity);

    @Mapping(target = "catalogId", source = "entity.parameter.id")
    ParameterResultRestDTO toRestDto(ParameterResult entity);

    List<ParameterResultRestDTO> toRestDto(List<ParameterResult> entity);


}
