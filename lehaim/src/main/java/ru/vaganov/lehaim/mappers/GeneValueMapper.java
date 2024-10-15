package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.vaganov.lehaim.dto.genes.GeneValueDTO;
import ru.vaganov.lehaim.models.genes.GeneValue;

@Mapper(componentModel = "spring")
public interface GeneValueMapper {

}
