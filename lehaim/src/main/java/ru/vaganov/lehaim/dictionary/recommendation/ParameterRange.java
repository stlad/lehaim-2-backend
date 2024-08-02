package ru.vaganov.lehaim.dictionary.recommendation;

public interface ParameterRange {

    /**
     * Начало интервала (включительно)
     */
    Double getStartRange();

    /**
     * Начало интервала (Исключительно)
     */
    Double getEndRange();

    default Boolean contains(Double value) {
        return value >= getStartRange() && value < getEndRange();
    }
}
