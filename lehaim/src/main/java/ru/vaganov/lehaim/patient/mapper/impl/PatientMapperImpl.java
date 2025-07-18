package ru.vaganov.lehaim.patient.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;
import ru.vaganov.lehaim.patient.mapper.PatientMapper;
import ru.vaganov.lehaim.patient.mapper.PatientRadiationTherapyMapper;
import ru.vaganov.lehaim.catalog.DiagnosisCatalogService;

import java.util.List;

import static ru.vaganov.lehaim.utils.MapperUtils.set;
import static ru.vaganov.lehaim.utils.MapperUtils.update;

@Component
@RequiredArgsConstructor
public class PatientMapperImpl implements PatientMapper {
    private final DiagnosisCatalogService diagnosisService;
    private final PatientRadiationTherapyMapper radiationTherapyMapper;

    public Patient fromDto(PatientDTO dto) {
        if (dto == null) {
            return null;
        }

        var patient = Patient.builder();

        set(patient::id, dto.getId());
        set(patient::name, dto.getName());
        set(patient::lastname, dto.getLastname());
        set(patient::patronymic, dto.getPatronymic());

        set(patient::birthdate, dto.getBirthdate());
        set(patient::deathdate, dto.getDeathdate());
        set(patient::operationDate, dto.getOperationDate());

        set(patient::t, dto.getT());
        set(patient::n, dto.getN());
        set(patient::m, dto.getM());
        set(patient::g, dto.getG());

        set(patient::additionalDiagnosis, dto.getAdditionalDiagnosis());
        set(patient::info, dto.getInfo());

        set(patient::operationComments, dto.getOperationComments());
        set(patient::diagnosisComments, dto.getDiagnosisComments());
        set(patient::chemotherapyComments, dto.getChemotherapyComments());
        set(patient::gender, dto.getGender());

        patient.radiationTherapy(radiationTherapyMapper.fromDTO(dto.getRadiationTherapy()));

        patient.diagnosis(dto.getDiagnosisId() == null ? null : diagnosisService.findById(dto.getDiagnosisId()));

        var patientEntity = patient.build();
        if (patientEntity.getRadiationTherapy() != null) {
            patientEntity.getRadiationTherapy().setPatient(patientEntity);
        }
        return patientEntity;
    }

    public PatientDTO toDto(Patient entity) {
        if (entity == null) {
            return null;
        }

        var dto = PatientDTO.builder();
        set(dto::diagnosisId, entity.getDiagnosis() == null ? null : entity.getDiagnosis().getId());
        set(dto::id, entity.getId());
        set(dto::name, entity.getName());
        set(dto::lastname, entity.getLastname());
        set(dto::patronymic, entity.getPatronymic());

        set(dto::birthdate, entity.getBirthdate());
        set(dto::deathdate, entity.getDeathdate());
        set(dto::operationDate, entity.getOperationDate());

        set(dto::info, entity.getInfo());
        set(dto::gender, entity.getGender());
        set(dto::operationComments, entity.getOperationComments());
        set(dto::diagnosisComments, entity.getDiagnosisComments());
        set(dto::chemotherapyComments, entity.getChemotherapyComments());
        set(dto::additionalDiagnosis, entity.getAdditionalDiagnosis());

        set(dto::t, entity.getT());
        set(dto::n, entity.getN());
        set(dto::m, entity.getM());
        set(dto::g, entity.getG());
        set(dto::radiationTherapy, radiationTherapyMapper.toDTO(entity.getRadiationTherapy()));
        return dto.build();
    }

    public List<PatientDTO> toDto(List<Patient> entity) {
        return entity.stream().map(this::toDto).toList();
    }

    public void updateFromDto(PatientDTO dto, Patient target) {
        if (dto == null) {
            return;
        }

        update(target::setId, dto.getId());
        update(target::setName, dto.getName());
        update(target::setLastname, dto.getLastname());
        update(target::setPatronymic, dto.getPatronymic());

        update(target::setT, dto.getT());
        update(target::setN, dto.getN());
        update(target::setG, dto.getG());
        update(target::setM, dto.getM());

        update(target::setAdditionalDiagnosis, dto.getAdditionalDiagnosis());
        update(target::setInfo, dto.getInfo());

        update(target::setOperationComments, dto.getOperationComments());
        update(target::setDiagnosisComments, dto.getDiagnosisComments());
        update(target::setChemotherapyComments, dto.getChemotherapyComments());

        update(target::setGender, dto.getGender());

        update(target::setDeathdate, dto.getDeathdate());
        update(target::setBirthdate, dto.getBirthdate());
        update(target::setOperationDate, dto.getOperationDate());

        if (dto.getDiagnosisId() != null) {
            update(target::setDiagnosis, diagnosisService.findById(dto.getDiagnosisId()));
        }

        if (dto.getRadiationTherapy() != null) {
            var therapy = target.getRadiationTherapy() == null
                    ? PatientRadiationTherapy.builder().patient(target).build()
                    : target.getRadiationTherapy();

            radiationTherapyMapper.updateFromDto(dto.getRadiationTherapy(), therapy);
            target.setRadiationTherapy(therapy);
        }
    }
}
