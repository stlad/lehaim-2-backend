package ru.vaganov.lehaim.recommendation.charts.tcell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vaganov.lehaim.recommendation.utils.ParameterRange;
import ru.vaganov.lehaim.recommendation.utils.ParameterRangeUtils;

import java.util.Arrays;

public class TParameterRanges {
    @Getter
    @AllArgsConstructor
    public enum NEU_CD3 implements ParameterRange {

        RANGE_1(0., 1.31),
        RANGE_2(1.32, 1.84),
        RANGE_3(1.84, 2.63),
        RANGE_4(2.63, 3.65),
        RANGE_5(3.65, 10.93),
        RANGE_6(10.93, 50.);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.NEU_CD3 of(Double value) {
            return ParameterRangeUtils.of(value, TParameterRanges.NEU_CD3.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_CD4 implements ParameterRange {
        RANGE_1(0., 1.75),
        RANGE_2(1.75, 2.45),
        RANGE_3(2.45, 3.5),
        RANGE_4(3.5, 5.1),
        RANGE_5(5.1, 15.01),
        RANGE_6(15.01, 100.);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.NEU_CD4 of(Double value) {
            return ParameterRangeUtils.of(value, TParameterRanges.NEU_CD4.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_CD8 implements ParameterRange {
        RANGE_1(0., 5.53),
        RANGE_2(5.53, 7.74),
        RANGE_3(7.74, 11.05),
        RANGE_4(11.05, 12.32),
        RANGE_5(12.32, 36.93),
        RANGE_6(36.93, 100.);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.NEU_CD8 of(Double value) {
            return ParameterRangeUtils.of(value, TParameterRanges.NEU_CD8.values());
        }
    }

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
        EMPTY(null, null),
        RANGE_1(0d, 0.2),
        RANGE_2(0.2, 0.5),
        RANGE_3(0.5, 4.);

        private final Double startRange;
        private final Double endRange;

        public static TParameterRanges.CD4 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(CD4.values()).filter(e -> e.getStartRange() != null).toArray(TParameterRanges.CD4[]::new));
        }
    }
}
