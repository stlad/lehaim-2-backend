package ru.vaganov.lehaim.dictionary.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vaganov.lehaim.dictionary.Gender;

public class RegenerationParameterRanges {

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

        public static NEU_LYMF of(Double value) {
            return ParameterRangeUtils.of(value, NEU_LYMF.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum NEU_MON implements ParameterRange {
        RANGE_1(0d, 2.1),
        RANGE_2(2.1, 3.2),
        RANGE_3(3.2, 4.48),
        RANGE_4(4.48, 6.4),
        RANGE_5(6.4, 12.8),
        RANGE_6(12.8, 32d),
        RANGE_7(32d, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static NEU_MON of(Double value) {
            return ParameterRangeUtils.of(value, NEU_MON.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum LYMF_MON implements ParameterRange {
        RANGE_1(0d, 1.1),
        RANGE_2(1.1, 1.7),
        RANGE_3(1.7, 2.38),
        RANGE_4(2.38, 3.4),
        RANGE_5(3.4, 6.1),
        RANGE_6(6.1, 15.25),
        RANGE_7(15.25, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static LYMF_MON of(Double value) {
            return ParameterRangeUtils.of(value, LYMF_MON.values());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum HEMOGLOBIN implements ParameterRange {
        FEMALE_RANGE_1(0d, 70d),
        FEMALE_RANGE_2(70d, 90d),
        FEMALE_RANGE_3(90d, 120d),
        FEMALE_RANGE_4(120d, 140d),
        FEMALE_RANGE_5(140d, 160d),
        FEMALE_RANGE_6(160d, 10000d),
        MALE_RANGE_1(0d, 70d),
        MALE_RANGE_2(70d, 90d),
        MALE_RANGE_3(90d, 130d),
        MALE_RANGE_4(130d, 161d),
        MALE_RANGE_5(161d, 171d),
        MALE_RANGE_6(171d, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static HEMOGLOBIN of(Double value, Gender gender) {
            HEMOGLOBIN[] values = gender.equals(Gender.Male) ?
                    new HEMOGLOBIN[]{MALE_RANGE_1, MALE_RANGE_2, MALE_RANGE_3,
                            MALE_RANGE_4, MALE_RANGE_5, MALE_RANGE_6} :
                    new HEMOGLOBIN[]{FEMALE_RANGE_1, FEMALE_RANGE_2, FEMALE_RANGE_3,
                            FEMALE_RANGE_4, FEMALE_RANGE_5, FEMALE_RANGE_6};
            return ParameterRangeUtils.of(value, values);
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
        RANGE_6(15d, 10000d);

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
        RANGE_5(350d, 10000d);

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
        RANGE_5(7.61, 10000d);

        private final Double startRange;
        private final Double endRange;

        public static NEUTROPHILS of(Double value) {
            return ParameterRangeUtils.of(value, NEUTROPHILS.values());
        }
    }
}
