package ru.vaganov.ResourceServer.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.RecommendationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@CrossOrigin("*")
public class RecommendationController {

    Logger logger = LoggerFactory.getLogger(RecommendationController.class);

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private OncologicalService oncologicalService;

    @GetMapping("/{testId}")
    public ResponseEntity<List<String>> solverTest(@PathVariable Long testId){
        logger.info("Request to /recommendations/" + testId);
        OncologicalTest test = oncologicalService.findTestById(testId);
        if(test == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<ParameterResult> results = oncologicalService.findResultsByTest(test);
        if(results == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<String> recs = new ArrayList<>();
        for(ParameterResult res: results){
            String recom = recommendationService.solveResult(res);
            if(recom=="") continue;
            recs.add(recom);
        }
        return new ResponseEntity<>(recs, HttpStatus.OK);
    }
}
