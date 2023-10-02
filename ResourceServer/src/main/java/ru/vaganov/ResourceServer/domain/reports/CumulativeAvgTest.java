package ru.vaganov.ResourceServer.domain.reports;

import lombok.Data;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CumulativeAvgTest {

    private Map<Long,CumulativeAvgResult> avgResults; // [param_id, cumresult]

    public CumulativeAvgTest(List<ParameterResult> finalResults){
        avgResults = new HashMap<>();
        for(ParameterResult res: finalResults){

            CumulativeAvgResult cumRes = new CumulativeAvgResult(res);
            avgResults.put(res.getParameter().getId(), cumRes);
        }
    }


    public void addNextTest(List<ParameterResult> results){
        for(ParameterResult res: results){
            if(avgResults.containsKey(res.getParameter().getId())){
                avgResults.get(res.getParameter().getId()).add(res);
            }

        }

    }

}

