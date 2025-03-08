package ru.vaganov.lehaim.patient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.exceptions.PatientExistsException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.patient.mapper.PatientMapper;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.patient.dto.PatientDTO;
import ru.vaganov.lehaim.repositories.PatientRadiationTherapyRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;
import ru.vaganov.lehaim.services.DiagnosisCatalogService;

import java.time.LocalDate;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final DiagnosisCatalogService diagnosisService;
    private final PatientRadiationTherapyRepository radiationTherapyRepository;

    public PatientDTO savePatient(PatientDTO dto) {
        if (isPatientPresent(dto))
            throw new PatientExistsException(dto.getLastname(), dto.getName(), dto.getPatronymic());

        Patient patient = patientMapper.fromDto(dto);
        if(patient.getRadiationTherapy() != null){
            patient.getRadiationTherapy().setPatient(patient);
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
                LocalDate.parse(dto.getBirthdate())).isPresent();
    }
}
