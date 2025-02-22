package ru.vaganov.lehaim.recommendation.charts.bcell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vaganov.lehaim.recommendation.utils.ParameterRange;
import ru.vaganov.lehaim.recommendation.utils.ParameterRangeUtils;

import java.util.Arrays;

public class BParameterRanges {

    @Getter
    @AllArgsConstructor
    public enum CD4 implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0d, 0.2),
        RANGE_2(0.2, 0.5),
        RANGE_3(0.5, 4.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.CD4 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(CD4.values()).filter(e->e.getStartRange()!=null).toArray(BParameterRanges.CD4[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CD19 implements ParameterRange {
        EMPTY(null, null),
        LESS(0., 0.11),
        RANGE_1(0.11, 0.21),
        RANGE_2(0.21, 0.41),
        RANGE_3(0.41, 0.81),
        RANGE_4(0.81, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static CD19 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(CD19.values()).filter(e->e.getStartRange()!=null)
                            .toArray(CD19[]::new));
        }
    }


    @Getter
    @AllArgsConstructor
    public enum CD19_TNK implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0., 0.02),
        RANGE_2(0.02, 0.04),
        RANGE_3(0.04, 0.11),
        GREATER(0.11, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static CD19_TNK of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(CD19_TNK.values()).filter(e->e.getStartRange()!=null)
                            .toArray(CD19_TNK[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum IgA implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0., 0.81),
        RANGE_2(0.81, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.IgA of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(IgA.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.IgA[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum IgG implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0., 0.61),
        RANGE_2(0.61, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.IgG of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(IgG.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.IgG[]::new));
        }
    }


    @Getter
    @AllArgsConstructor
    public enum NEU_LYMF implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0d, 0.5),
        RANGE_2(0.5, 0.85),
        RANGE_3(0.85, 1.17),
        RANGE_4(1.17, 1.67),
        RANGE_5(1.67, 2.1),
        RANGE_6(2.1, 5.25),
        RANGE_7(5.25, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.NEU_LYMF of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(NEU_LYMF.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.NEU_LYMF[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_CD19 implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0d, 8.),
        RANGE_2(8., 11.2),
        RANGE_3(11.2, 16.),
        RANGE_4(16., 21.01),
        RANGE_5(21.01, 36.01),
        RANGE_6(36.01, 100.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.NEU_CD19 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(NEU_CD19.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.NEU_CD19[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_CD4 implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0d, 0.085),
        RANGE_2(0.085, 0.12),
        RANGE_3(0.12, 0.17),
        RANGE_4(0.17, 0.32),
        RANGE_5(0.32, 0.37),
        RANGE_6(0.37, 100.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.NEU_CD4 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(NEU_CD4.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.NEU_CD4[]::new));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CD19_CD4 implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0d, 0.265),
        RANGE_2(0.265, 0.37),
        RANGE_3(0.37, 0.53),
        RANGE_4(0.53, 0.78),
        RANGE_5(0.78, 2.32),
        RANGE_6(2.32, 100.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.CD19_CD4 of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(CD19_CD4.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.CD19_CD4[]::new));
        }
    }
}
