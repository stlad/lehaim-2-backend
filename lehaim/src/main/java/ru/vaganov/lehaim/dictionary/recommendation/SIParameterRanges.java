package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SIParameterRanges {
    @Getter
    @AllArgsConstructor
    public enum Siri implements ParameterRange {
        RANGE_1(0d, 0.25),
        RANGE_2(0.25, 0.61),
        RANGE_3(0.61, 1.34),
        RANGE_4(1.34, 4.),
        RANGE_5(4., 10000.);

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
        RANGE_1(0d, 0.51),
        RANGE_2(0.51, 0.72),
        RANGE_3(0.72, 0.96),
        RANGE_4(0.96, 1.43),
        RANGE_5(1.43, 1.51),
        RANGE_6(1.51, 4.51),
        RANGE_7(4.51, 10000.);

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
        RANGE_2(37.8, 180.61),
        RANGE_3(180.61, 466.68),
        RANGE_4(466.68, 1400.02),
        RANGE_5(1400.02, 10000.01),
        RANGE_6(10000.01, 25000.);

        private final Double startRange;
        private final Double endRange;

        public static SIParameterRanges.Piv of(Double value) {
            return ParameterRangeUtils.of(value, SIParameterRanges.Piv.values());
        }
    }
}
