package ru.vaganov.lehaim.mappers;

import org.mapstruct.*;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.dto.PatientDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {DiagnosisMapper.class})
public interface PatientMapper {


    public abstract Patient fromDto(PatientDTO dto);
    @Mapping(source = "entity.diagnosis.id", target = "diagnosisId")
    public abstract  PatientDTO toDto(Patient entity);
    public abstract List<PatientDTO> toDto(List<Patient> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);
}

