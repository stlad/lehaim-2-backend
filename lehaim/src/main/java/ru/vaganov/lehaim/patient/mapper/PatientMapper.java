package ru.vaganov.lehaim.patient.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.mappers.DiagnosisMapper;
import ru.vaganov.lehaim.utils.MapperUtils;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.services.DiagnosisCatalogService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        DiagnosisMapper.class,
        DiagnosisCatalogService.class,
        PatientRadiationTherapyMapper.class
})
public abstract class PatientMapper {
    @Autowired
    protected DiagnosisCatalogService diagnosisService;

    @Mapping(target = "diagnosis", expression = "java(dto.getDiagnosisId() == null ? null : diagnosisService.findById(dto.getDiagnosisId()))")
    public abstract Patient fromDto(PatientDTO dto);

    @Mapping(source = "entity.diagnosis.id", target = "diagnosisId")
    public abstract PatientDTO toDto(Patient entity);

    public abstract List<PatientDTO> toDto(List<Patient> entity);


    @BeforeMapping
    protected void beforeMapping(PatientDTO dto, @MappingTarget Patient entity) {
        MapperUtils.updateDateField(entity::setDeathdate, dto.getDeathdate());
        MapperUtils.updateDateField(entity::setBirthdate, dto.getBirthdate());
        MapperUtils.updateDateField(entity::setOperationDate, dto.getOperationDate());

        if (dto.getDiagnosisId() != null) {
            entity.setDiagnosis(diagnosisService.findById(dto.getDiagnosisId()));
        }
    }

    @Mapping(target = "deathdate", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "operationDate", ignore = true)
    @Mapping(target = "diagnosis", ignore = true)
    @Mapping(target = "radiationTherapy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);


}

