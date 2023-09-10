package ru.vaganov.ResourceServer.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.PatientService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getALlPatients(){
        return new ResponseEntity<>(patientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id){
        Patient patient = patientService.findById(id);
        if (patient==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatientById(@PathVariable Long id){
        Patient pat = patientService.findById(id);
        if(pat == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        patientService.delete(pat);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient){
        Patient patientToChange = patientService.findById(patient.getId());
        if(patientToChange == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        patientToChange.updateFieldsBy(patient);
        patientToChange = patientService.save(patientToChange);
        return new ResponseEntity<>(patientToChange, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Patient> newPatient(@RequestBody Patient patient){
        patient = patientService.save(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
}
