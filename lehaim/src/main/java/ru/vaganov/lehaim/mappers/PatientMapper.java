package ru.vaganov.lehaim.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.dto.PatientDTO;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.services.DiagnosisCatalogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

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
        if (dto.getDeathdate() != null) {
            setDate(entity::setDeathdate, dto.getDeathdate());
        }

        if (dto.getBirthdate() != null) {
            setDate(entity::setBirthdate, dto.getBirthdate());
        }

        if (dto.getOperationDate() != null) {
            setDate(entity::setOperationDate, dto.getOperationDate());
        }

        if (dto.getDiagnosisId() != null) {
            entity.setDiagnosis(diagnosisService.findById(dto.getDiagnosisId()));
        }
    }

    @Mapping(target = "deathdate", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "operationDate", ignore = true)
    @Mapping(target = "diagnosis", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(PatientDTO dto, @MappingTarget Patient entity);

    private void setDate(Consumer<LocalDate> set, String date) {
        set.accept(
                date.isEmpty()
                        ? null
                        : LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }
}

