package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.DiagnosisNotFoundException;
import ru.vaganov.ResourceServer.exceptions.PatientAlreadyExistsException;
import ru.vaganov.ResourceServer.exceptions.PatientNotFoundException;
import ru.vaganov.ResourceServer.mappers.PatientMapper;
import ru.vaganov.ResourceServer.models.Diagnosis;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.time.LocalDate;
import java.util.UUID;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private DiagnosisRepo diagnosisRepo;

    public PatientDTO savePatient(PatientDTO dto){
        if(isPatientPresent(dto))
            throw new PatientAlreadyExistsException(dto.getLastname(), dto.getName(), dto.getPatronymic());

        Patient patient =patientMapper.fromDto(dto);
        if(dto.getDiagnosisId() != null){
            Diagnosis diagnosis = diagnosisRepo.findById(dto.getDiagnosisId())
                    .orElseThrow(()-> new DiagnosisNotFoundException(dto.getDiagnosisId()));
            patient.setDiagnosis(diagnosis);
        }
        patient = patientRepo.save(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientById(UUID id){
        Patient patient = patientRepo.findById(id)
                .orElseThrow(()->new PatientNotFoundException(id));

        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientByFullNameAndBirthdate(
            String firstname, String lastname, String middlename, LocalDate birthdate){
        Patient patient;
        if(middlename==null){
             patient = patientRepo.findByNameAndLastnameAndBirthdate(firstname, lastname, birthdate)
                    .orElseThrow(()->new PatientNotFoundException(firstname,lastname,"",birthdate));
        }
        else{
            patient = patientRepo.findByNameAndLastnameAndPatronymicAndBirthdate(firstname, lastname, middlename, birthdate)
                    .orElseThrow(()->new PatientNotFoundException(firstname,lastname,middlename,birthdate));
        }

        return patientMapper.toDto(patient);
    }

    public PatientDTO updatePatient(UUID id, PatientDTO dto){
        Patient patient = patientRepo.findById(id)
                .orElseThrow(()->new PatientNotFoundException(id));

        dto.setId(null);
        patientMapper.updateFromDto(dto, patient);
        if(dto.getDiagnosisId() != null){
            Diagnosis diagnosis = diagnosisRepo.findById(dto.getDiagnosisId())
                    .orElseThrow(()-> new DiagnosisNotFoundException(dto.getDiagnosisId()));
            patient.setDiagnosis(diagnosis);
        }
        return patientMapper.toDto(patientRepo.save(patient));
    }

    @Deprecated(forRemoval = true)
    public Patient findById(UUID id){
        return patientRepo.findById(id)
                .orElseThrow(()->new PatientNotFoundException(id));
    }

    private boolean isPatientPresent(PatientDTO dto){
        return patientRepo.findByNameAndLastnameAndPatronymicAndBirthdate(
                dto.getName(),
                dto.getLastname(),
                dto.getPatronymic(),
                dto.getBirthdate()).isPresent();
    }



}
