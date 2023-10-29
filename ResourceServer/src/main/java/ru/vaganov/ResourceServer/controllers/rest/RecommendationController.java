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
        logger.info("Solving test with id: "+testId);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecommendation(@PathVariable Long id){
        logger.info("deleting recommendation with id: " + id);
        Expression expression = recommendationService.findById(id);
        if(expression == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Expression> editRecommendation(@RequestBody Expression newExpression){
        logger.info("editing recommendation with id: " + newExpression.getId());
        Expression expression = recommendationService.findById(newExpression.getId());
        if(expression == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        expression.updateFieldsBy(newExpression);
        expression = recommendationService.saveExpression(expression);
        return new ResponseEntity<>(expression, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Expression>> getAllExpressions(){
        return new ResponseEntity<>(recommendationService.findAllExpressions(), HttpStatus.OK);
    }
}
