package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.dto.ParameterDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
    public abstract Parameter fromDto(ParameterDTO dto);
    public abstract  ParameterDTO toDto(Parameter entity);
    public abstract List<ParameterDTO> toDto(List<Parameter> entity);
}
