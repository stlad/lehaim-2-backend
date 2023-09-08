package ru.vaganov.ResourceServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getALlPatients(){
        return new ResponseEntity<>(patientService.findAll(), HttpStatus.OK);
    }
}
