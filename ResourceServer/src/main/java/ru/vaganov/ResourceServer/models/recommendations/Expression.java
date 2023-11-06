package ru.vaganov.ResourceServer.models.recommendations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data @Builder @NoArgsConstructor @AllArgsConstructor
//@Entity
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expression;
    private String trueResult;
    private String falseResult;
    private String cause;



    public void updateFieldsBy(Expression changes){
        if(changes.getExpression()!=null) setExpression(changes.getExpression());
        if(changes.getTrueResult()!=null) setTrueResult(changes.getTrueResult());
        if(changes.getFalseResult()!=null) setFalseResult(changes.getFalseResult());
        if(changes.getCause()!=null) setCause(changes.getCause());
    }

}
