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

    @Getter
    @AllArgsConstructor
    public enum HEMOGLOBIN_MALE implements ParameterRange {
        RANGE_1(0d, 70d),
        RANGE_2(70d, 90d),
        RANGE_3(90d, 130d),
        RANGE_4(130d, 161d),
        RANGE_5(161d, 171d),
        RANGE_6(171d, 1000d);

        private final Double startRange;
        private final Double endRange;

        public static HEMOGLOBIN_MALE of(Double value) {
            return ParameterRangeUtils.of(value, HEMOGLOBIN_MALE.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum HEMOGLOBIN_FEMALE implements ParameterRange {
        RANGE_1(0d, 70d),
        RANGE_2(70d, 90d),
        RANGE_3(90d, 120d),
        RANGE_4(120d, 140d),
        RANGE_5(140d, 160d),
        RANGE_6(160d, 1000d);

        private final Double startRange;
        private final Double endRange;

        public static HEMOGLOBIN_FEMALE of(Double value) {
            return ParameterRangeUtils.of(value, HEMOGLOBIN_FEMALE.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum LEUKOCYTES implements ParameterRange {
        RANGE_1(0d, 1d),
        RANGE_2(1d, 2d),
        RANGE_3(2d, 3d),
        RANGE_4(3d, 4d),
        RANGE_5(4d, 15d),
        RANGE_6(15d, 1000d);

        private final Double startRange;
        private final Double endRange;

        public static LEUKOCYTES of(Double value) {
            return ParameterRangeUtils.of(value, LEUKOCYTES.values());
        }
    }

    /**
     * Тромбоциты
     */
    @Getter
    @AllArgsConstructor
    public enum PLATELETS implements ParameterRange {
        RANGE_1(0d, 21d),
        RANGE_2(21d, 50d),
        RANGE_3(50d, 180d),
        RANGE_4(180d, 350d),
        RANGE_5(350d, 1000d);

        private final Double startRange;
        private final Double endRange;

        public static PLATELETS of(Double value) {
            return ParameterRangeUtils.of(value, PLATELETS.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEUTROPHILS implements ParameterRange {
        RANGE_1(0d, 0.75),
        RANGE_2(0.75, 1d),
        RANGE_3(1d, 1.5),
        RANGE_4(1.5, 7.61),
        RANGE_5(7.61, 1000d);

        private final Double startRange;
        private final Double endRange;

        public static NEUTROPHILS of(Double value) {
            return ParameterRangeUtils.of(value, NEUTROPHILS.values());
        }
    }
}
