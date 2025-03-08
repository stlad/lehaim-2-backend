package ru.vaganov.lehaim.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.dto.PatientDTO;
import ru.vaganov.lehaim.services.DiagnosisCatalogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {DiagnosisMapper.class, DiagnosisCatalogService.class})
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
        if(!dto.getDeathdate().isEmpty()){
            entity.setDeathdate(LocalDate.parse(dto.getDeathdate(), DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if(!dto.getBirthdate().isEmpty()){
            entity.setBirthdate(LocalDate.parse(dto.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if(!dto.getOperationDate().isEmpty()){
            entity.setOperationDate(LocalDate.parse(dto.getOperationDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        }

        if(dto.getDiagnosisId() != null){
            entity.setDiagnosis(diagnosisService.findById(dto.getDiagnosisId()));
        }
    }

    @Mapping(target = "deathdate", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "operationDate", ignore = true)
    @Mapping(target = "diagnosis", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);


}

