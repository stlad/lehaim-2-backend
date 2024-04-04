package ru.vaganov.ResourceServer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.recommendations.IntervalRecommendation;
import ru.vaganov.ResourceServer.repositories.PatientRepo;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.RecommendationService;

import java.util.List;

@Configuration
@Slf4j
public class DataLoadingConfig {

    @Autowired DataInitialyzer initialyzer;

    @ConditionalOnProperty(
            prefix = "command-line-runner.data-loading.patients",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    public CommandLineRunner patientDataLoader(PatientRepo patientRepo) {
        return args -> {
            if(patientRepo.findAll().size() != 0){
                log.info("test patients already loaded");
                return;
            }
            log.info("Loading test patients");
            initialyzer.loadTestPatient();
            log.info("Test Patients loading completed");
        };
    }


    @ConditionalOnProperty(
            prefix = "command-line-runner.data-loading.recommendations",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    @Bean
    public CommandLineRunner expressionDataLoader(RecommendationService recommendationService,CatalogService catalogService) {
        return args -> {
            log.info("Loading test recommendations");
            List<Parameter> catalog = catalogService.findAll();

            IntervalRecommendation irec = IntervalRecommendation.builder()
                    .parameter(catalogService.findById(1L))
                    .resultIfGreater("Лейкоциты ПРЕВЫШАЮТ норму => пить чай")
                    .resultIfInside("Лейкоциты ВНУТРИ нормы => лечение не нужно")
                    .resultIfLess("Лейкоциты НИЖЕ нормы => пить кофе")
                    .build();
            recommendationService.save(irec);

            irec = IntervalRecommendation.builder()
                    .parameter(catalogService.findById(2L))
                    .resultIfGreater("Лимфоциты ПРЕВЫШАЮТ норму => кушать тортики")
                    .resultIfInside("Лимфоциты ВНУТРИ нормы => лечение не нужно")
                    .resultIfLess("Лимфоциты НИЖЕ нормы => кушать пирожки")
                    .build();
            recommendationService.save(irec);

            irec = IntervalRecommendation.builder()
                    .parameter(catalogService.findById(3L))
                    .resultIfGreater("Моноциты ПРЕВЫШАЮТ норму => кушать конфетки")
                    .resultIfInside("Моноциты ВНУТРИ нормы => лечение не нужно")
                    .resultIfLess("Моноциты НИЖЕ нормы => кушать мармеладки")
                    .build();
            recommendationService.save(irec);


            irec = IntervalRecommendation.builder()
                    .parameter(catalogService.findById(4L))
                    .resultIfGreater("Нейтрофилы ПРЕВЫШАЮТ норму => смотреть киношки")
                    .resultIfInside("Нейтрофилы ВНУТРИ нормы => лечение не нужно")
                    .resultIfLess("Нейтрофилы НИЖЕ нормы => смотреть сериальчики")
                    .build();
            recommendationService.save(irec);
            log.info("Test recommendations loading completed");
        };
    }
}
