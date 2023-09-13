package ru.vaganov.ResourceServer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.config.DataLoadingConfig;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.util.List;

@Service
public class PatientService {
    Logger logger = LoggerFactory.getLogger(PatientService.class);
    private PatientRepo patientRepo;


    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public Patient save(Patient patient){
        patient = patientRepo.save(patient);
        logger.debug("Patient [" + patient.toString() + "] saved");
        return patient;
    }

    public void delete(Patient patient){
        patientRepo.delete(patient);
        logger.debug("Patient [" + patient.toString() + "] deleted");
    }

    public Patient findById(Long id){
        return patientRepo.findById(id).orElse(null);
    }

    public List<Patient> findAll(){
        return patientRepo.findAll();
    }
}
