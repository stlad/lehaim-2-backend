package ru.vaganov.lehaim.catalog.mapper;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.gene.GeneMapper;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;
import ru.vaganov.lehaim.catalog.dto.DiagnosisDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {GeneMapper.class})
public interface DiagnosisMapper {
    Diagnosis fromDto(DiagnosisDTO dto);
    DiagnosisDTO toDto(Diagnosis entity);
    List<DiagnosisDTO> toDto(List<Diagnosis> entity);
}
