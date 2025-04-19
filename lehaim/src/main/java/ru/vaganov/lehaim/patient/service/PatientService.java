package ru.vaganov.lehaim.patient.service;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vaganov.lehaim.exceptions.PatientExistsException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.patient.mapper.PatientMapper;
import ru.vaganov.lehaim.patient.repository.PatientRepository;

import java.time.LocalDate;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Transactional
    public PatientDTO savePatient(PatientDTO dto) {
        Patient patient = patientMapper.fromDto(dto);
        patient = save(patient);
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO findPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO findPatientByFullNameAndBirthdate(
            String firstname, String lastname, String middlename, LocalDate birthdate) {
        var patient = patientRepository.findSinglePatient(firstname, lastname, middlename, birthdate)
                .orElseThrow(() -> new PatientNotFoundException(firstname, lastname, middlename, birthdate));
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO updatePatient(UUID id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        dto.setId(null);
        patientMapper.updateFromDto(dto, patient);
        patient = save(patient);
        return patientMapper.toDto(patient);
    }

    @Deprecated(forRemoval = true)
    public Patient findById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private Patient save(Patient patient){
        try {
            return patientRepository.saveAndFlush(patient);
        } catch (DataIntegrityViolationException exception){
            log.error(exception.getMessage());
            throw new PatientExistsException(patient);
        }
    }
}
