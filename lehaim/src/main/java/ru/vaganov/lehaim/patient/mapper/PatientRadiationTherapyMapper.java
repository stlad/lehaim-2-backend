package ru.vaganov.lehaim.patient.mapper;

import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;


public interface PatientRadiationTherapyMapper {

    PatientRadiationTherapyDTO toDTO(PatientRadiationTherapy entity);

    PatientRadiationTherapy fromDTO(PatientRadiationTherapyDTO dto);

    void updateFromDto(PatientRadiationTherapyDTO dto, PatientRadiationTherapy entity);
}

