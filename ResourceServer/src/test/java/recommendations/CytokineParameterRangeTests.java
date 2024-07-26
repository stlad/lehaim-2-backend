package recommendations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vaganov.ResourceServer.dictionary.recommendation.CytokineParameterRange;

public class CytokineParameterRangeTests {

    @Test
    @DisplayName("cytokineParameterRange.of")
    void cytokineParameterRange_of(){
        Assertions.assertEquals(CytokineParameterRange.RANGE_1, CytokineParameterRange.of(3d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_2, CytokineParameterRange.of(44d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_3, CytokineParameterRange.of(119d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_4, CytokineParameterRange.of(120d));
        Assertions.assertEquals(CytokineParameterRange.RANGE_5, CytokineParameterRange.of(600d));
    }
}
