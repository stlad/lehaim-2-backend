package ru.vaganov.ResourceServer.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reports")
@Slf4j
public class ReportController {

    @GetMapping("/{id}")
    public ResponseEntity<> getReportByTestId(@PathVariable Long id){
        return new ResponseEntity<>(null, HttpStatus.OK)
    }


}
