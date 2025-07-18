package ru.vaganov.lehaim.catalog.mapper;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.catalog.entity.Parameter;
import ru.vaganov.lehaim.catalog.dto.ParameterDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
    public abstract Parameter fromDto(ParameterDTO dto);
    public abstract  ParameterDTO toDto(Parameter entity);
    public abstract List<ParameterDTO> toDto(List<Parameter> entity);
}
