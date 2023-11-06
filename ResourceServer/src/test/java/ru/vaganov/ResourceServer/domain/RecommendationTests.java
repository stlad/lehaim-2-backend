package ru.vaganov.ResourceServer.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vaganov.ResourceServer.domain.recommendations.BinaryRecommendationSolver;
import ru.vaganov.ResourceServer.models.recommendations.Expression;

public class RecommendationTests {

    private BinaryRecommendationSolver solver = new BinaryRecommendationSolver();


    @Test
    public void simpleSolverExpression(){
        Expression expression = Expression.builder()
                .expression("2_>_1").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("true", solver.process(expression, null));
    }

    @Test
    public void simpleSolverAndExpression(){
        Expression expression = Expression.builder()
                .expression("2_>_1 and 1_<_10").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("true", solver.process(expression, null));
    }

    @Test
    public void simpleSolverAndFalseExpression(){
        Expression expression = Expression.builder()
                .expression("2_>_1 and 1_>_10").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("false", solver.process(expression, null));
    }

    @Test
    public void simpleSolverOrTrueExpression(){
        Expression expression = Expression.builder()
                .expression("2_>_1 or 1_>_10").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("true", solver.process(expression, null));
    }

    @Test
    public void simpleSolverOrFalseExpression(){
        Expression expression = Expression.builder()
                .expression("2_<_1 or 1_>_10").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("false", solver.process(expression, null));
    }

    @Test
    public void simpleSolverOrTrue1Expression(){
        Expression expression = Expression.builder()
                .expression("2_<_1 or 1_<_10").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("true", solver.process(expression, null));
    }

    @Test
    public void simpleSolverAndOrAndExpression(){
        Expression expression = Expression.builder()
                .expression("2_<_1 and 1_<_10 or 2_>_1 and 10_>_1").trueResult("true").falseResult("false").build();
        Assertions.assertEquals("true", solver.process(expression, null));
    }
}
