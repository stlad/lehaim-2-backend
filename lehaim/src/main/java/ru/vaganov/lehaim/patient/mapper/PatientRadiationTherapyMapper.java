package ru.vaganov.lehaim.patient.mapper;

import org.mapstruct.*;
import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.utils.MapperUtils;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;


@Mapper(componentModel = "spring")
public abstract class PatientRadiationTherapyMapper {

    abstract PatientRadiationTherapyDTO toDTO(PatientRadiationTherapy entity);

    abstract PatientRadiationTherapy fromDTO(PatientRadiationTherapyDTO dto);

    @BeforeMapping
    protected void beforeMapping(PatientRadiationTherapyDTO dto, @MappingTarget PatientRadiationTherapy entity) {
        MapperUtils.updateDateField(entity::setStartTherapy, dto.getStartTherapy());
        MapperUtils.updateDateField(entity::setEndTherapy, dto.getEndTherapy());
    }

    @Mapping(target = "startTherapy", ignore = true)
    @Mapping(target = "endTherapy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updateFromDto(PatientRadiationTherapyDTO dto, @MappingTarget PatientRadiationTherapy entity);
}

