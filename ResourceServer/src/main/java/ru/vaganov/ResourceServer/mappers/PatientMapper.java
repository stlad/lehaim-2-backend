package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.*;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientMapper {

    public abstract Patient fromDto(PatientDTO dto);
    public abstract  PatientDTO toDto(Patient entity);
    public abstract List<PatientDTO> toDto(List<Patient> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);
}
