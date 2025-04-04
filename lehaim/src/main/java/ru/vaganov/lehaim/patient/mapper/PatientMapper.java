package ru.vaganov.lehaim.patient.mapper;

import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.patient.entity.Patient;

import java.util.List;

public interface PatientMapper {

    Patient fromDto(PatientDTO dto);

    PatientDTO toDto(Patient entity);

    List<PatientDTO> toDto(List<Patient> entity);

    void updateFromDto(PatientDTO dto, Patient entity);
}