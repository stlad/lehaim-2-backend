package ru.vaganov.ResourceServer.parsers;


import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import ru.vaganov.ResourceServer.models.Parameter;

import java.io.File;
import java.io.IOException;

public class CsvParserTests {

    @Test
    public void CorrectFromatParameterParsing() throws IOException {
        File file = new ClassPathResource("Test Catalog").getFile();

        CsvFileParser<Parameter> parser = new CsvFileParser<>(file);
        parser.exec(str->{
            String[] arr = str.split(";");
            Parameter parameter = Parameter.builder()
                    .name(arr[0])
                    .additionalName(arr[1])
                    .unit(arr[2])
                    .refMin(Double.parseDouble(arr[3]))
                    .refMax(Double.parseDouble(arr[4]))
                    .researchType(Parameter.ResearchType.valueOf(arr[5]))
                    .build();
            return parameter;
        }, System.out::println);

    }
}
