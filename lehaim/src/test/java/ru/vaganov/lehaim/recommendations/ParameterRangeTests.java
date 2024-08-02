package ru.vaganov.lehaim.recommendations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vaganov.lehaim.dictionary.recommendation.CytokineParameterRange;
import ru.vaganov.lehaim.dictionary.recommendation.RegenerationParameterRanges;

public class ParameterRangeTests {

    @Test
    @DisplayName("cytokineParameterRange.of")
    void cytokineParameterRange_of(){
        Assertions.assertEquals(CytokineParameterRange.RANGE_1, CytokineParameterRange.of(3d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_2, CytokineParameterRange.of(44d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_3, CytokineParameterRange.of(119d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_4, CytokineParameterRange.of(120d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_5, CytokineParameterRange.of(600d));
    }

    @Test
    @DisplayName("RegenerationParameterRange.NEU_LYMF.of")
    void RegenerationParameterRange_NEU_LYMF(){
        Assertions.assertEquals(RegenerationParameterRanges.NEU_LYMF.RANGE_1,
                RegenerationParameterRanges.NEU_LYMF.of(1d));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_LYMF.RANGE_2,
                RegenerationParameterRanges.NEU_LYMF.of(1.17));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_LYMF.RANGE_3,
                RegenerationParameterRanges.NEU_LYMF.of(1.7));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_LYMF.RANGE_4,
                RegenerationParameterRanges.NEU_LYMF.of(5.1));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_LYMF.RANGE_5,
                RegenerationParameterRanges.NEU_LYMF.of(20d));
    }

    @Test
    @DisplayName("RegenerationParameterRange.NEU_MON.of")
    void RegenerationParameterRange_NEU_MON(){
        Assertions.assertEquals(RegenerationParameterRanges.NEU_MON.RANGE_1,
                RegenerationParameterRanges.NEU_MON.of(1d));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_MON.RANGE_2,
                RegenerationParameterRanges.NEU_MON.of(5d));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_MON.RANGE_3,
                RegenerationParameterRanges.NEU_MON.of(7d));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_MON.RANGE_4,
                RegenerationParameterRanges.NEU_MON.of(30d));
        Assertions.assertEquals(RegenerationParameterRanges.NEU_MON.RANGE_5,
                RegenerationParameterRanges.NEU_MON.of(100d));
    }

    @Test
    @DisplayName("RegenerationParameterRange.LYMF_MON.of")
    void RegenerationParameterRange_LYMF_MONF(){
        Assertions.assertEquals(RegenerationParameterRanges.LYMF_MON.RANGE_1,
                RegenerationParameterRanges.LYMF_MON.of(2d));
        Assertions.assertEquals(RegenerationParameterRanges.LYMF_MON.RANGE_2,
                RegenerationParameterRanges.LYMF_MON.of(3d));
        Assertions.assertEquals(RegenerationParameterRanges.LYMF_MON.RANGE_3,
                RegenerationParameterRanges.LYMF_MON.of(5d));
        Assertions.assertEquals(RegenerationParameterRanges.LYMF_MON.RANGE_4,
                RegenerationParameterRanges.LYMF_MON.of(7d));
        Assertions.assertEquals(RegenerationParameterRanges.LYMF_MON.RANGE_5,
                RegenerationParameterRanges.LYMF_MON.of(20d));
    }
}
