package ru.vaganov.lehaim.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.vaganov.lehaim.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.models.PatientRadiationTherapy;


@Mapper(componentModel = "spring")
public interface PatientRadiationTherapyMapper {

    PatientRadiationTherapyDTO toDTO(PatientRadiationTherapy entity);

    PatientRadiationTherapy fromDTO(PatientRadiationTherapyDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PatientRadiationTherapyDTO dto, @MappingTarget PatientRadiationTherapy entity);
}

