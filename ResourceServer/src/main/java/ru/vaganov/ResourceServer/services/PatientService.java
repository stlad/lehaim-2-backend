package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.ValidationException;
import ru.vaganov.ResourceServer.mappers.PatientMapper;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.dto.PatientDTO;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.time.LocalDate;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientMapper patientMapper;

    public PatientDTO savePatient(PatientDTO dto){
        if(isPatientPresent(dto))
            throw new ValidationException(String.format("Patient: %s %s already exists", dto.getLastname(), dto.getName()));
        Patient patient =patientRepo.save(patientMapper.fromDto(dto));

        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientById(Long id){
        Patient patient = patientRepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cannot find patient with id: "+id));

        return patientMapper.toDto(patient);
    }

    public PatientDTO findPatientByFullNameAndBirthdate(
            String firstname, String lastname, String middlename, LocalDate birthdate){
        Patient patient;
        if(middlename==null){
             patient = patientRepo.findByNameAndLastnameAndBirthdate(firstname, lastname, birthdate)
                    .orElseThrow(()->new EntityNotFoundException(String.format("Cannot find patient: %s %s %s", firstname, lastname, birthdate)));
        }
        else{
            patient = patientRepo.findByNameAndLastnameAndPatronymicAndBirthdate(firstname, lastname, middlename, birthdate)
                    .orElseThrow(()->new EntityNotFoundException(String.format("Cannot find patient: %s %s %s %s", firstname, lastname, middlename, birthdate)));
        }

        return patientMapper.toDto(patient);
    }

    public PatientDTO updatePatient(Long id, PatientDTO dto){
        Patient patient = patientRepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cannot find patient with id: "+id));

        dto.setId(null);
        patientMapper.updateFromDto(dto, patient);
        return patientMapper.toDto(patientRepo.save(patient));
    }

    @Deprecated(forRemoval = true)
    public Patient findById(Long id){
        return patientRepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cannot find patient with id: "+id));
    }

    private boolean isPatientPresent(PatientDTO dto){
        return patientRepo.findByNameAndLastnameAndPatronymicAndBirthdate(
                dto.getName(),
                dto.getLastname(),
                dto.getPatronymic(),
                dto.getBirthdate()).isPresent();
    }



}
