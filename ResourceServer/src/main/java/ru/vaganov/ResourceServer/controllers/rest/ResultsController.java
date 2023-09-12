package ru.vaganov.ResourceServer.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.PatientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/results")
public class ResultsController {

    @Autowired
    private CatalogService catalogService;
    @Autowired private PatientService patientService;
    @Autowired private OncologicalService oncologicalService;

    @GetMapping("/{id}")
    public ResponseEntity<ParameterResult> getResultById(@PathVariable Long id){
        ParameterResult res = oncologicalService.findResultById(id);
        if (res==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ParameterResult> updateResult(@RequestBody ParameterResult parameterResult){
        ParameterResult foundRes = oncologicalService.findResultById(parameterResult.getId());
        if (foundRes==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        foundRes.setValue(parameterResult.getValue());
        foundRes = oncologicalService.saveResult(foundRes);
        return new ResponseEntity<>(foundRes, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ParameterResult> saveResult(@RequestBody ParameterResult parameterResult){
        OncologicalTest oncologicalTest = oncologicalService.findTestById(parameterResult.getAttachedTest().getId());
        Parameter parameter = catalogService.findById(parameterResult.getParameter().getId());

        if (oncologicalTest==null || parameter == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        parameterResult.setAttachedTest(oncologicalTest);
        parameterResult.setParameter(parameter);
        parameterResult = oncologicalService.saveResult(parameterResult);
        return new ResponseEntity<>(parameterResult, HttpStatus.OK);

    }
}
