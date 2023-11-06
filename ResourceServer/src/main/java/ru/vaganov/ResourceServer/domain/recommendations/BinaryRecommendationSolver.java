package ru.vaganov.ResourceServer.domain.recommendations;

import ru.vaganov.ResourceServer.models.recommendations.Expression;
import ru.vaganov.ResourceServer.models.ParameterResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class BinaryRecommendationSolver{


    public String process(Expression expression, List<ParameterResult> results){
        Boolean res = parse(expression.getExpression(), results);
        if(res) return  expression.getTrueResult();
        else return  expression.getFalseResult();

        /*  ПРИМЕР ИСПОЛЬЗОВАНИЯ

         List<String> recs = new ArrayList<>();
        for(Expression expr: expressions) {
            recs.add(solver.process(expr, resultList));
        }
         */
    }

    private Boolean parse(String str, List<ParameterResult> results){
        String[] a = str.split(" ");
        List<String> exprWithBools = new ArrayList<>();
        Arrays.stream(a).forEach(cell->{
            String[] splittedCell =cell.split("_");
            if(splittedCell.length == 3){
                Boolean cellRes = cellToBool(splittedCell, results);
                exprWithBools.add(cellRes.toString());
            }else {
                exprWithBools.add(cell);
            }
        });
//        System.out.println(exprWithBools);
        List<String> exprWithBools1 = new ArrayList<>();
        int i =0;
        while (i<exprWithBools.size()){
            if(exprWithBools.get(i).equals("and")){
                Boolean lval = Boolean.parseBoolean(exprWithBools.get(i-1));
                Boolean rval = Boolean.parseBoolean(exprWithBools.get(i+1));
                Boolean res = lval && rval;
                exprWithBools.set(i, res.toString());
                exprWithBools.remove(i+1);
                exprWithBools.remove(i-1);
            }
            i++;
        }

        i=0;
        while (i<exprWithBools.size()){
            if(exprWithBools.get(i).equals("or")){
                Boolean lval = Boolean.parseBoolean(exprWithBools.get(i-1));
                Boolean rval = Boolean.parseBoolean(exprWithBools.get(i+1));
                Boolean res = lval || rval;
                exprWithBools.set(i, res.toString());
                exprWithBools.remove(i+1);
                exprWithBools.remove(i-1);
            }
            i++;
        }
        return Boolean.parseBoolean(exprWithBools.get(0));
    }


    private boolean cellToBool(String[] cell, List<ParameterResult> results){
        Double lval;
        Double rval;
        if(cell[0].startsWith("!")){
            //TODO ПОЛУЧИТЬ ЗНАЧЕНИЕ ИЗ КАТАЛОГА
            Long id = Long.parseLong(cell[0].substring(1));
            lval = results.stream().filter(res->res.getParameter().getId()==id)
                    .map(ParameterResult::getValue)
                    .findFirst().orElse(0.);
        }else{
            lval = Double.parseDouble(cell[0]);
        }

        if(cell[2].startsWith("!")){
            //TODO ПОЛУЧИТЬ ЗНАЧЕНИЕ ИЗ КАТАЛОГА
            Long id = Long.parseLong(cell[2].substring(1));
            rval = results.stream().filter(res->res.getParameter().getId()==id)
                    .map(ParameterResult::getValue)
                    .findFirst().orElse(0.);
        }else{
            rval = Double.parseDouble(cell[2]);
        }

        switch (cell[1]){
            case ">": return lval > rval;
            case ">=": return lval >= rval;
            case "<": return lval < rval;
            case "<=": return lval <= rval;
            case "==": return lval == rval;
            case "!=": return lval != rval;
            default: return false;
        }
    }
}
