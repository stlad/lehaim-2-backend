package ru.vaganov.lehaim.gene;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.vaganov.lehaim.gene.dto.GeneDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueInputDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueOutputDTO;
import ru.vaganov.lehaim.gene.dto.GeneValueOutputListDTO;
import ru.vaganov.lehaim.gene.entity.DiagnosisGene;
import ru.vaganov.lehaim.gene.entity.Gene;
import ru.vaganov.lehaim.gene.entity.GeneValue;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeneMapper {
    String SEPARATOR = ";";

    @Mapping(target = "possibleValues", expression = "java(entity.getPossibleValues().split(\"" + SEPARATOR + "\"))")
    GeneDTO toDTO(Gene entity);

    List<GeneDTO> toDTO(List<Gene> entity);

    @Mapping(target = "geneId", source = "diagnosisGene.gene.id")
    @Mapping(target = "diagnosisId", source = "diagnosisGene.diagnosis.id")
    GeneValueInputDTO toInputDTO(GeneValue entity);

    @Mapping(target = "geneId", source = "diagnosisGene.gene.id")
    @Mapping(target = "diagnosisId", source = "diagnosisGene.diagnosis.id")
    GeneValueOutputDTO toOutputDTO(GeneValue entity);

    List<GeneValueOutputDTO> toOutputDTO(List<GeneValue> entity);

    GeneValueOutputListDTO toListOutputDto(UUID patientId, List<GeneValue> values);

    @Mapping(target = "id", source = "gene.id")
    @Mapping(target = "geneName", source = "gene.geneName")
    @Mapping(target = "possibleValues", expression = "java(diagnosisGene.getGene().getPossibleValues().split(\"" + SEPARATOR + "\"))")
    @Mapping(target = "defaultValue", source = "gene.defaultValue")
    GeneDTO extractGeneDTO(DiagnosisGene diagnosisGene);
}
