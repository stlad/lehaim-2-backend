package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CytokineParameterRange implements ParameterRange{
    RANGE_1(0d, 40d),
    RANGE_2(40d, 80d),
    RANGE_3(80d, 120d),
    RANGE_4(120d, 160d),
    RANGE_5(160d, 1000d);

    private final Double startRange;
    private final Double endRange;

    public static CytokineParameterRange of(Double value) {
        return ParameterRangeUtils.of(value, CytokineParameterRange.values());
    }
}
