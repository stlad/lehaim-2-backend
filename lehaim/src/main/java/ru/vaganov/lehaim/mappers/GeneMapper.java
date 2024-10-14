package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vaganov.lehaim.dto.genes.GeneDTO;
import ru.vaganov.lehaim.models.genes.Gene;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneMapper {

    @Mapping(target = "possibleValues", expression = "java(entity.getPossibleValues().split(\";\"))")
    GeneDTO toDTO(Gene entity);

    List<GeneDTO> toDTO(List<Gene> entity);
}
