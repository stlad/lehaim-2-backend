package ru.vaganov.lehaim.patient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        throwIfExists(dto.getName(), dto.getLastname(), dto.getPatronymic(), LocalDate.parse(dto.getBirthdate()));
        Patient patient = patientMapper.fromDto(dto);
        patient = patientRepository.save(patient);
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
        throwIfExists(patient.getName(), patient.getLastname(), patient.getPatronymic(), patient.getBirthdate());
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Deprecated(forRemoval = true)
    public Patient findById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private void throwIfExists(String firstname, String lastname, String patronymic, LocalDate birthdate) {
        var patient = patientRepository.findSinglePatient(
                firstname,
                lastname,
                patronymic,
                birthdate);
        if (patient.isPresent()) {
            throw new PatientExistsException(patient.get());
        }
    }
}
