package ru.vaganov.ResourceServer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.DiagnosisNotFoundException;
import ru.vaganov.ResourceServer.exceptions.PatientExistsException;
import ru.vaganov.ResourceServer.exceptions.PatientNotFoundException;
import ru.vaganov.ResourceServer.mappers.PatientMapper;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepository;
import ru.vaganov.ResourceServer.repositories.PatientRepository;

import java.time.LocalDate;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final DiagnosisRepository diagnosisRepository;

    public PatientDTO savePatient(PatientDTO dto) {
        if (isPatientPresent(dto))
            throw new PatientExistsException(dto.getLastname(), dto.getName(), dto.getPatronymic());

        Patient patient = patientMapper.fromDto(dto);
        if (dto.getDiagnosisId() != null) {
            Diagnosis diagnosis = diagnosisRepository.findById(dto.getDiagnosisId())
                    .orElseThrow(() -> new DiagnosisNotFoundException(dto.getDiagnosisId()));
            patient.setDiagnosis(diagnosis);
        }
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientByFullNameAndBirthdate(
            String firstname, String lastname, String middlename, LocalDate birthdate) {
        Patient patient;
        if (middlename == null) {
            patient = patientRepository.findByNameAndLastnameAndBirthdate(firstname, lastname, birthdate)
                    .orElseThrow(() -> new PatientNotFoundException(firstname, lastname, "", birthdate));
        } else {
            patient = patientRepository.findByNameAndLastnameAndPatronymicAndBirthdate(firstname, lastname, middlename, birthdate)
                    .orElseThrow(() -> new PatientNotFoundException(firstname, lastname, middlename, birthdate));
        }

        return patientMapper.toDto(patient);
    }

    public PatientDTO updatePatient(UUID id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        dto.setId(null);
        patientMapper.updateFromDto(dto, patient);
        if (dto.getDiagnosisId() != null) {
            Diagnosis diagnosis = diagnosisRepository.findById(dto.getDiagnosisId())
                    .orElseThrow(() -> new DiagnosisNotFoundException(dto.getDiagnosisId()));
            patient.setDiagnosis(diagnosis);
        }
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Deprecated(forRemoval = true)
    public Patient findById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private boolean isPatientPresent(PatientDTO dto) {
        return patientRepository.findByNameAndLastnameAndPatronymicAndBirthdate(
                dto.getName(),
                dto.getLastname(),
                dto.getPatronymic(),
                dto.getBirthdate()).isPresent();
    }
}
