package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class, OncologicalTestMapper.class})
public interface ParameterResultMapper {

    public abstract ParameterResult fromDto(ParameterResultDTO dto);
    public abstract  ParameterResultDTO toDto(ParameterResult entity);
    public abstract List<ParameterResultDTO> toDto(List<ParameterResult> entity);

    @Mapping(target = "catalogId", source = "entity.parameter.id")
    public abstract ParameterResultRestDTO toRestDto(ParameterResult entity);

}
