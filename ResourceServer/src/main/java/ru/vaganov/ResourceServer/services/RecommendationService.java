package ru.vaganov.ResourceServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.Expression;
import ru.vaganov.ResourceServer.repositories.ExpressionRepo;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    ExpressionRepo expressionRepo;

    public List<Expression> findAllExpressions(){
        return expressionRepo.findAll();
    }

    public Expression saveExpression(Expression expression){
        return expressionRepo.save(expression);
    }
}
