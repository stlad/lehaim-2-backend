package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RegenerationParameterRanges {

    @Getter
    @AllArgsConstructor
    public enum NEU_LYMF implements ParameterRange {
        RANGE_1(0d, 1.17),
        RANGE_2(1.17, 1.67),
        RANGE_3(1.67, 2.1),
        RANGE_4(2.1, 5.25),
        RANGE_5(5.25, 25d);

        private final Double startRange;
        private final Double endRange;

        public static NEU_LYMF of(Double value) {
            return ParameterRangeUtils.of(value, NEU_LYMF.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_MON implements ParameterRange {
        RANGE_1(0d, 4.48),
        RANGE_2(4.48, 6.4),
        RANGE_3(6.4, 12.8),
        RANGE_4(12.8, 32d),
        RANGE_5(32d, 130d);

        private final Double startRange;
        private final Double endRange;

        public static NEU_MON of(Double value) {
            return ParameterRangeUtils.of(value, NEU_MON.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum LYMF_MON implements ParameterRange {
        RANGE_1(0d, 2.38),
        RANGE_2(2.38, 3.4),
        RANGE_3(3.4, 6.1),
        RANGE_4(6.1, 15.25),
        RANGE_5(15.25, 100d);

        private final Double startRange;
        private final Double endRange;

        public static LYMF_MON of(Double value) {
            return ParameterRangeUtils.of(value, LYMF_MON.values());
        }
    }
}
