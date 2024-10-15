package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.vaganov.lehaim.dto.genes.GeneDTO;
import ru.vaganov.lehaim.dto.genes.GeneValueDTO;
import ru.vaganov.lehaim.models.genes.Gene;
import ru.vaganov.lehaim.models.genes.GeneValue;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface GeneMapper {

    @Mapping(target = "possibleValues", expression = "java(entity.getPossibleValues().split(\";\"))")
    GeneDTO toDTO(Gene entity);

    List<GeneDTO> toDTO(List<Gene> entity);

    @Mapping(target = "geneId", source = "diagnosisGene.gene.id")
    @Mapping(target = "diagnosisId", source = "diagnosisGene.diagnosis.id")
    @Mapping(target = "patientId", source = "patient.id")
    GeneValueDTO toDTO(GeneValue entity);
}
