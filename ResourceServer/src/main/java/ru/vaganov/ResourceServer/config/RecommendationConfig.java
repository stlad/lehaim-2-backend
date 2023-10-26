package ru.vaganov.ResourceServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vaganov.ResourceServer.domain.recommendations.RecommendationSolver;
import ru.vaganov.ResourceServer.domain.recommendations.StringExpressionSolver;

@Configuration
public class RecommendationConfig {

    @Bean
    RecommendationSolver getRecSolver(){
        return new StringExpressionSolver();
    }
}
