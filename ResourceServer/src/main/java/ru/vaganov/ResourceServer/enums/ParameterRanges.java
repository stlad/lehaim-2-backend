package ru.vaganov.ResourceServer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParameterRanges {
    RANGE_1(0d, 40d),
    RANGE_2(40d, 80d),
    RANGE_3(80d, 120d),
    RANGE_4(120d, 160d),
    RANGE_5(160d, 1000d);

    /**
     * Начало интервала (включительно)
     */
    private final Double startRange;

    /**
     * Начало интервала (Исключительно)
     */
    private final Double endRange;

    public Boolean contains(Double value) {
        return value >= getStartRange() || value < getEndRange();
    }

    public static ParameterRanges of(Double value) {
        for (ParameterRanges range : ParameterRanges.values()) {
            if (range.contains(value)) {
                return range;
            }
        }
        throw new IllegalArgumentException("Недопустимое значение " + value + " параметра вне диапазона");
    }
}
