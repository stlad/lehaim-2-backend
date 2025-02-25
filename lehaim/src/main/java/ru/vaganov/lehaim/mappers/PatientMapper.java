package ru.vaganov.lehaim.mappers;

import org.mapstruct.*;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.dto.PatientDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {DiagnosisMapper.class, PatientRadiationTherapyMapper.class})
public interface PatientMapper {


    Patient fromDto(PatientDTO dto);
    @Mapping(source = "entity.diagnosis.id", target = "diagnosisId")
    PatientDTO toDto(Patient entity);
    List<PatientDTO> toDto(List<Patient> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);
}

