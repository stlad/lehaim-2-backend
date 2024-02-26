package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.dto.ParameterResultDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class, OncologicalTestMapper.class})
public interface ParameterResultMapper {

    public abstract ParameterResult fromDto(ParameterResultDTO dto);
    public abstract  ParameterResultDTO toDto(ParameterResult entity);
    public abstract List<ParameterResultDTO> toDto(List<ParameterResult> entity);
}
