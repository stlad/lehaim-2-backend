package ru.vaganov.lehaim.recommendation.charts.cytokine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vaganov.lehaim.recommendation.utils.ParameterRange;
import ru.vaganov.lehaim.recommendation.utils.ParameterRangeUtils;

@Getter
@AllArgsConstructor
public enum CytokineParameterRange implements ParameterRange {
    RANGE_1(0d, 26.68),
    RANGE_2(26.68, 40d),
    RANGE_3(40d, 80d),
    RANGE_4(80d, 120d),
    RANGE_5(120d, 160d),
    RANGE_6(160d, 10000d);

    private final Double startRange;
    private final Double endRange;

    public static CytokineParameterRange of(Double value) {
        return ParameterRangeUtils.of(value, CytokineParameterRange.values());
    }
}
