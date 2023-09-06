package ru.vaganov.ResourceServer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.services.CatalogService;

@SpringBootTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CatalogServiceTests {


    @Autowired
    private CatalogService catalogService;

    @Test
    public void updatingParameter(){
        Parameter param1 = Parameter.builder().name("Тестовый параметр").additionalName("TEST").build();
        param1 = catalogService.save(param1);
        Assertions.assertNull(param1.getRefMax());
        param1.setRefMax(100.);
        param1 = catalogService.save(param1);
        Assertions.assertEquals(100.,param1.getRefMax());

        catalogService.delete(param1);
    }

    @Test
    public void cannotSaveParamWithSimilarNameAndAddName(){
        Parameter param1 = Parameter.builder().name("Тестовый параметр").additionalName("TEST").build();
        Parameter param2 = Parameter.builder().name("Тестовый параметр").additionalName("TEST").build();

        Parameter res1 = catalogService.save(param1);
        Parameter res2 = catalogService.save(param2);

        Assertions.assertEquals(res1.getId(), res2.getId());

        catalogService.delete(param1);
        catalogService.delete(param2);
    }

    @Test
    @Disabled
    public void updatingParameterNameToAlreadyExisting(){
        Parameter param1 = Parameter.builder().name("Тестовый параметр ОДИН").additionalName("TEST").refMax(100.).build();
        Parameter param2 = Parameter.builder().name("Тестовый параметр ДВА").additionalName("TEST").build();

        param1 = catalogService.save(param1);
        param2 = catalogService.save(param2);

        param2.setName("Тестовый параметр ОДИН");
        catalogService.save(param2);

        catalogService.delete(param1);
        catalogService.delete(param2);
    }

}

