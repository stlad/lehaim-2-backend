package ru.vaganov.ResourceServer.domain.reports;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;

@Data
public class CumulativeAvgResult{

    private Double finalValue;
    private Parameter parameter;
    private Double avg;

    @Getter(AccessLevel.NONE)
    private Double sum;
    @Getter(AccessLevel.NONE)
    private Integer count;


    public void add(ParameterResult res){
        sum+=res.getValue();
        count++;
        avg = count==0 ? 0 : sum / count;
    }


    public CumulativeAvgResult(ParameterResult finalRes){
        this.setParameter(finalRes.getParameter());
        this.setFinalValue(finalRes.getValue());
        this.setSum(0.);
        this.setCount(0);
        this.setAvg(0.);
    }
}
