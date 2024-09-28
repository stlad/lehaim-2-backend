package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SIParameterRanges {
    @Getter
    @AllArgsConstructor
    public enum Siri implements ParameterRange {
        RANGE_1(0d, 0.25),
        RANGE_2(0.25, 1.34),
        RANGE_3(1.34, 4.),
        RANGE_4(4., 10000.);

        private final Double startRange;
        private final Double endRange;

        public static SIParameterRanges.Siri of(Double value) {
            return ParameterRangeUtils.of(value, SIParameterRanges.Siri.values());
        }
    }

    /**
     * Плотность нейтрофилов
     */
    @Getter
    @AllArgsConstructor
    public enum NeuDensity implements ParameterRange {
        RANGE_1(0d, 1.43),
        RANGE_2(1.43, 1.51),
        RANGE_3(1.51, 4.51),
        RANGE_4(4.51, 10000.);

        private final Double startRange;
        private final Double endRange;

        public static SIParameterRanges.NeuDensity of(Double value) {
            return ParameterRangeUtils.of(value, SIParameterRanges.NeuDensity.values());
        }
    }

    /**
     * Плотность нейтрофилов
     */
    @Getter
    @AllArgsConstructor
    public enum Piv implements ParameterRange {
        RANGE_1(0d, 37.8),
        RANGE_2(37.8, 466.68),
        RANGE_3(466.68, 1400.02),
        RANGE_4(1400.02, 10000.);

        private final Double startRange;
        private final Double endRange;

        public static SIParameterRanges.Piv of(Double value) {
            return ParameterRangeUtils.of(value, SIParameterRanges.Piv.values());
        }
    }
}
