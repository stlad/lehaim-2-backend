package ru.vaganov.ResourceServer.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterResultsTests {

    @Test
    public void paramResultValidationOnInitTest(){
        Parameter parameter = new Parameter();
        parameter.setName("Parameter for test");
        parameter.setRefMin(10.);
        parameter.setRefMax(20.);

        ParameterResult result = new ParameterResult();
        Assertions.assertFalse(result.isValid());
        result.setParameter(parameter);

        result.setValue( 10.); Assertions.assertTrue(result.isValid());
        result.setValue( 15.); Assertions.assertTrue(result.isValid());
        result.setValue( 20.); Assertions.assertTrue(result.isValid());

        result.setValue(-15.); Assertions.assertFalse(result.isValid());
        result.setValue( 25.); Assertions.assertFalse(result.isValid());

    }
}
