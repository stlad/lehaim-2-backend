package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TParameterRanges {
    @Getter
    @AllArgsConstructor
    public enum NEU_LYMF implements ParameterRange {
        RANGE_1(0d, 0.5),
        RANGE_2(0.5, 0.85),
        RANGE_3(0.85, 1.17),
        RANGE_4(1.17, 1.67),
        RANGE_5(1.67, 2.1),
        RANGE_6(2.1, 5.25),
        RANGE_7(5.25, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.NEU_LYMF of(Double value) {
            return ParameterRangeUtils.of(value, TParameterRanges.NEU_LYMF.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CD4 implements ParameterRange {
        RANGE_1(0d, 0.2),
        RANGE_2(0.2, 0.5),
        RANGE_3(0.5, 4.);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.CD4 of(Double value) {
            return ParameterRangeUtils.of(value, TParameterRanges.CD4.values());
        }
    }
}
