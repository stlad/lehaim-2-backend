package ru.vaganov.ResourceServer.mappers;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.dto.DiagnosisDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DiagnosisMapper {
    public abstract Diagnosis fromDto(DiagnosisDTO dto);
    public abstract  DiagnosisDTO toDto(Diagnosis entity);
    public abstract List<DiagnosisDTO> toDto(List<Diagnosis> entity);
}
