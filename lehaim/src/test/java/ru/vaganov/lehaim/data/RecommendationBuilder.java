package ru.vaganov.lehaim.data;


import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.recommendation.Recommendation;
import ru.vaganov.lehaim.recommendation.RecommendationRepository;

import java.time.LocalDateTime;

public class RecommendationBuilder {

    private final RecommendationRepository recommendationRepository;
    private Recommendation recommendation;

    public RecommendationBuilder(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
        this.recommendation = Recommendation.builder()
                .recommendation("Рекомендация по умолчанию")
                .conclusion("Заключение по умолчанию")
                .name("Название по умолчанию")
                .dateCreated(LocalDateTime.now())
                .chartType(ChartType.T_CELL)
                .build();
    }

    public RecommendationBuilder withChart(ChartType chart){
        this.recommendation.setChartType(chart);
        return this;
    }

    public RecommendationBuilder withRecommendation(String recommendation){
        this.recommendation.setRecommendation(recommendation);
        return this;
    }

    public Recommendation buildAndSave(){
        return recommendationRepository.save(this.recommendation);
    }

    public Recommendation build(){
        return this.recommendation;
    }
}
