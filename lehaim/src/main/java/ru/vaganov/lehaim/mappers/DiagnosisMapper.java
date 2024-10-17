package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.dto.DiagnosisDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {GeneMapper.class})
public interface DiagnosisMapper {
    Diagnosis fromDto(DiagnosisDTO dto);
    DiagnosisDTO toDto(Diagnosis entity);
    List<DiagnosisDTO> toDto(List<Diagnosis> entity);
}
