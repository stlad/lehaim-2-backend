package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.models.Parameter;
import ru.vaganov.lehaim.dto.ParameterDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
    public abstract Parameter fromDto(ParameterDTO dto);
    public abstract  ParameterDTO toDto(Parameter entity);
    public abstract List<ParameterDTO> toDto(List<Parameter> entity);
}
