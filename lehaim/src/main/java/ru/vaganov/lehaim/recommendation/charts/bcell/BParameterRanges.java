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
    public enum B_LYMF implements ParameterRange {
        EMPTY(null, null),
        LESS(0., 0.11),
        RANGE_1(0.11, 0.21),
        RANGE_2(0.21, 0.41),
        RANGE_3(0.41, 0.81),
        RANGE_4(0.81, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.B_LYMF of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(B_LYMF.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.B_LYMF[]::new));
        }
    }


    @Getter
    @AllArgsConstructor
    public enum B_LYMF_TNK implements ParameterRange {
        EMPTY(null, null),
        RANGE_1(0., 0.02),
        RANGE_2(0.02, 0.04),
        RANGE_3(0.04, 0.11),
        GREATER(0.11, 1000.);

        private final Double startRange;
        private final Double endRange;

        public static BParameterRanges.B_LYMF_TNK of(Double value) {
            return ParameterRangeUtils.of(value,
                    Arrays.stream(B_LYMF_TNK.values()).filter(e->e.getStartRange()!=null)
                            .toArray(BParameterRanges.B_LYMF_TNK[]::new));
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

        public static BParameterRanges.NEU_LYMF of(Double value) {
            return ParameterRangeUtils.of(value, BParameterRanges.NEU_LYMF.values());
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


}
