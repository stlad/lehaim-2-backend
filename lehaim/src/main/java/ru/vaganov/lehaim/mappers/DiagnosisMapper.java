package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.models.Diagnosis;
import ru.vaganov.lehaim.dto.DiagnosisDTO;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DiagnosisMapper {
    public abstract Diagnosis fromDto(DiagnosisDTO dto);
    public abstract  DiagnosisDTO toDto(Diagnosis entity);
    public abstract List<DiagnosisDTO> toDto(List<Diagnosis> entity);
}
