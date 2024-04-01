package ru.vaganov.ResourceServer.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;
import ru.vaganov.ResourceServer.services.OncologicalServiceDeprecated;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/results")
public class ResultsController {

    @Autowired private OncologicalServiceDeprecated oncologicalServiceDeprecated;

    @GetMapping("/tests/{patientId}/all")
    public ResponseEntity<List<OncologicalTestDTO>> getAllTestsByPatientId(@PathVariable Long patientId){


    }

}
