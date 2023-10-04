package ru.vaganov.ResourceServer.controllers.rest;


import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.domain.reports.ReportData;
import ru.vaganov.ResourceServer.domain.reports.ReportFactory;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.PatientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reports")
public class ReportController {


    @Autowired private CatalogService catalogService;
    @Autowired private PatientService patientService;
    @Autowired private OncologicalService oncologicalService;
    @Autowired private ReportFactory reportFactory;
    Logger logger = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/{id}")
    public ResponseEntity<ReportData> getReportByTestId(@PathVariable Long id){
        OncologicalTest test = oncologicalService.findTestById(id);
        if(test == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reportFactory.CreateReportDataByTest(test), HttpStatus.OK);

    }


}
