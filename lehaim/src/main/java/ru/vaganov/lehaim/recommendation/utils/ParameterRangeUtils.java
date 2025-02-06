package ru.vaganov.lehaim.recommendation.utils;

public class ParameterRangeUtils {
    public static <T extends ParameterRange> T of(Double value, T[] values) {
        for (T range : values) {
            if (range.contains(value)) {
                return range;
            }
        }
        throw new IllegalArgumentException("Недопустимое значение " + value + " параметра вне диапазона");

    }
}
