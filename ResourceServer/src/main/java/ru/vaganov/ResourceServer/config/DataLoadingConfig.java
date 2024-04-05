package ru.vaganov.ResourceServer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

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

}
