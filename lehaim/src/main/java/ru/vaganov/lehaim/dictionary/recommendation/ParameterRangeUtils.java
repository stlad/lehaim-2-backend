package ru.vaganov.lehaim.dictionary.recommendation;

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
