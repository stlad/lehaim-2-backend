package ru.vaganov.lehaim.recommendation.charts.tcell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.BaseContextTest;
import ru.vaganov.lehaim.dictionary.ChartType;


class TCellChartServiceTest extends BaseContextTest {

    @Autowired
    private TCellChartService tCellChartService;

    @Test
    void getChart() {
        Assertions.assertEquals(ChartType.T_CELL, tCellChartService.getChart());
    }

    @Test
    void saveRecommendation() {
    }

    @Test
    void getRecommendation() {
    }
}