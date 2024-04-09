package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;

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

