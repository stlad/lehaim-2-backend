package ru.vaganov.ResourceServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.vaganov.ResourceServer.models.Expression;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.parsers.CsvFileParser;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.PatientService;
import ru.vaganov.ResourceServer.services.RecommendationService;

import java.io.File;

@Configuration
public class DataLoadingConfig {

    Logger logger = LoggerFactory.getLogger(DataLoadingConfig.class);

    @ConditionalOnProperty(
            prefix = "command-line-runner.data-loading.catalog",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    public CommandLineRunner dataLoader(CatalogService catalogService) {
        return args -> {
            //System.out.println("ЗАГРУЗКА КАТАЛОГА");
            logger.info("Loading catalog from \"Catalog\" file");

            CsvFileParser<Parameter> parser = new CsvFileParser<>("/Catalog");
            parser.exec(str -> {
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
            logger.info("Catalog loading completed");
        };
    }


    @Autowired DataInitialyzer initialyzer;

    @ConditionalOnProperty(
            prefix = "command-line-runner.data-loading.patients",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    public CommandLineRunner patientDataLoader(PatientService patientService) {
        return args -> {
            if(patientService.findAll().size() != 0){
                logger.info("test patients already loaded");
                return;
            }
            logger.info("Loading test patients");
            initialyzer.loadTestPatient();
            logger.info("Test Patients loading completed");
        };
    }


    @ConditionalOnProperty(
            prefix = "command-line-runner.data-loading.recommendations",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    public CommandLineRunner expressionDataLoader(RecommendationService recommendationService) {
        return args -> {
            logger.info("Loading test recommendations");
            Expression expression1 = Expression.builder()
                    .expression("!1_>=_4 and !1_<=_11")
                    .trueResult("Лейкоциты внутри нормы")
                    .falseResult("Лейкоциты вне нормы")
                    .build();


            Expression expression2 = Expression.builder()
                    .expression("!2_>=_1 and !2_<=_4.8")
                    .trueResult("Лимфоциты внутри нормы")
                    .falseResult("Лимфоциты вне нормы")
                    .build();
            recommendationService.saveExpression(expression1);
            recommendationService.saveExpression(expression2);
            logger.info("test recomme loading completed");
        };
    }
}
