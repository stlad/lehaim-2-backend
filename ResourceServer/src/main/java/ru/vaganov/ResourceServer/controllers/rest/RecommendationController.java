package ru.vaganov.ResourceServer.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.domain.recommendations.Solver;
import ru.vaganov.ResourceServer.models.Expression;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.RecommendationService;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recommendations")
public class RecommendationController {

    Logger logger = LoggerFactory.getLogger(RecommendationController.class);
    @Autowired
    private OncologicalService oncologicalService;
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private Solver solver;

    @GetMapping("/{testId}")
    public ResponseEntity<List<String>> solveTest(@PathVariable Long testId){
        OncologicalTest test = oncologicalService.findTestById(testId);
        if(test == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<ParameterResult> resultList = oncologicalService.findResultsByTest(test);
        if(resultList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Expression> expressions = recommendationService.findAllExpressions();
        if(expressions == null) return new ResponseEntity<>(null, HttpStatus.OK);

        List<String> recs = new ArrayList<>();
        for(Expression expr: expressions) {
            recs.add(solver.process(expr, resultList));
        }
        return new ResponseEntity<>(recs, HttpStatus.OK);
    }
}
