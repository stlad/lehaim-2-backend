package ru.vaganov.ResourceServer.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.recommendations.IntervalRecommendation;
import ru.vaganov.ResourceServer.repositories.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntervalRecJpaTests {
    @Autowired
    private CatalogRepo catalogRepo;
    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private IntervalRecRepo intervalRecRepo;

    @Test
    public void savingWorks(){
        Parameter parameter = Parameter.builder().refMin(1.).refMax(10.).name("test param").build();
        parameter = catalogRepo.save(parameter);

        IntervalRecommendation rec = IntervalRecommendation.builder().parameter(parameter).build();
        rec = intervalRecRepo.save(rec);

        catalogRepo.delete(parameter);
        Assertions.assertEquals(0,intervalRecRepo.findAll().size());
    }
}