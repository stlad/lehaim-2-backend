package ru.vaganov.ResourceServer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.parsers.CsvFileParser;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.services.CatalogService;

import java.io.File;

@Configuration
public class DataLoadingConfig {

    @Bean
    public CommandLineRunner catalogDataLoader(CatalogService catalogService){
        return args->{
            File file = new ClassPathResource("Catalog").getFile();

            CsvFileParser<Parameter> parser = new CsvFileParser<>(file);
            parser.go(str->{
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

            }, catalogService::save);

        };
    }
}
