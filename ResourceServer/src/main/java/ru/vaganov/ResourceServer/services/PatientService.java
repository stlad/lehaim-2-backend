package ru.vaganov.ResourceServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.util.List;

@Service
public class PatientService {
    private PatientRepo patientRepo;


    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public Patient save(Patient patient){
        return patientRepo.save(patient);
    }

    public void delete(Patient patient){
        patientRepo.delete(patient);
    }

    public Patient findById(Long id){
        return patientRepo.findById(id).orElse(null);
    }

    public List<Patient> findAll(){
        return patientRepo.findAll();
    }
}
