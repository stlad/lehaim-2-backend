package ru.vaganov.ResourceServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.recommendations.IntervalRecommendation;
import ru.vaganov.ResourceServer.repositories.IntervalRecRepo;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    IntervalRecRepo intervalRepo;

    public List<IntervalRecommendation> findAllExpressions(){
        return intervalRepo.findAll();
    }

    public IntervalRecommendation save(IntervalRecommendation intervalRecommendation){
        return intervalRepo.save(intervalRecommendation);
    }

    public void deleteExpression(IntervalRecommendation expression){
        intervalRepo.delete(expression);
    }

    public IntervalRecommendation findById(Long id){
        return intervalRepo.findById(id).orElse(null);
    }

    public String solveResult(ParameterResult result){
        IntervalRecommendation rec = intervalRepo.findByParameter(result.getParameter());
        if(rec==null) return "";
        if(result.getValue() > result.getParameter().getRefMax())
            return rec.getResultIfGreater();
        else if (result.getValue() < result.getParameter().getRefMin())
            return rec.getResultIfLess();
        else
            return rec.getResultIfInside();
    }
}
