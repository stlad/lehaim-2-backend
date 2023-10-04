package ru.vaganov.ResourceServer.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vaganov.ResourceServer.domain.reports.CumulativeAvgResult;
import ru.vaganov.ResourceServer.domain.reports.CumulativeAvgTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.PatientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportDataTests {


    @Test
    public void CumulativeResultWorksCorrect(){
        Parameter parameter1 = Parameter.builder().id(1L).name("Тест параметр 1").additionalName(" ").refMin(0d).refMax(1d).build();

        ParameterResult finalP = ParameterResult.builder().parameter(parameter1).value(5.).build();
        ParameterResult r1 = ParameterResult.builder().parameter(parameter1).value(1.).build();
        ParameterResult r2 = ParameterResult.builder().parameter(parameter1).value(2.).build();
        ParameterResult r3 = ParameterResult.builder().parameter(parameter1).value(6.).build();

        CumulativeAvgResult cumRes = new CumulativeAvgResult(finalP);
        cumRes.add(r1);
        Assertions.assertEquals(1,cumRes.getAvg());
        cumRes.add(r2);
        Assertions.assertEquals(1.5,cumRes.getAvg());
        cumRes.add(r3);
        Assertions.assertEquals(3,cumRes.getAvg());

    }

    @Test
    public void CumulativeAvgTestWorksCorrect(){
        //TODO дописать тест на работоспособоность
        Parameter parameter1 = Parameter.builder().id(1L).name("Тест параметр 1").additionalName(" ").refMin(0d).refMax(1d).build();
        Parameter parameter2 = Parameter.builder().id(2L).name("Тест параметр 2").additionalName(" ").refMin(0d).refMax(1d).build();

        List<ParameterResult> results1 = new ArrayList<>();
        List<ParameterResult> results2 = new ArrayList<>();
        List<ParameterResult> resultsFinal = new ArrayList<>();

        resultsFinal.add(ParameterResult.builder().parameter(parameter1).value(5.).build());
        resultsFinal.add(ParameterResult.builder().parameter(parameter2).value(10.).build());


        results1.add(ParameterResult.builder().parameter(parameter1).value(1.).build());
        results1.add(ParameterResult.builder().parameter(parameter2).value(6.).build());

        results2.add(ParameterResult.builder().parameter(parameter1).value(5.).build());
        results2.add(ParameterResult.builder().parameter(parameter2).value(12.).build());

        CumulativeAvgTest cumTest = new CumulativeAvgTest(resultsFinal);
        cumTest.addNextTest(results1);
        Assertions.assertEquals(1, cumTest.getAvgResults().get(1L).getAvg()); //среднее по одному анализу
        Assertions.assertEquals(6, cumTest.getAvgResults().get(2L).getAvg());

        cumTest.addNextTest(results2);
        Assertions.assertEquals(3, cumTest.getAvgResults().get(1L).getAvg()); //среднее по двум анализам
        Assertions.assertEquals(9, cumTest.getAvgResults().get(2L).getAvg());



        Assertions.assertEquals(5, cumTest.getAvgResults().get(1L).getFinalValue()); // последний результат (не уситывается при подсчете среднего )
        Assertions.assertEquals(10, cumTest.getAvgResults().get(2L).getFinalValue());
    }


}
