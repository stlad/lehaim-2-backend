package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientMapper {
    public abstract Patient fromDto(PatientDTO dto);
    public abstract  PatientDTO toDto(Patient entity);
    public abstract List<PatientDTO> toDto(List<Patient> entity);
}
