package ru.vaganov.ResourceServer.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CatalogJpaTests {

    @Autowired
    private CatalogRepo catalogRepo;

    @Test
    public void cannotSaveParamWithSimilarNameAndAddName(){
        Parameter param1 = Parameter.builder().name("ТЕСТ1").additionalName("ТТТ1").build();
        Parameter param2 = Parameter.builder().name("ТЕСТ1").additionalName("ТТТ2").build();
        Parameter param3 = Parameter.builder().name("ТЕСТ2").additionalName("ТТТ1").build();
        Parameter param4 = Parameter.builder().name("ТЕСТ1").additionalName("ТТТ1").build();

        catalogRepo.save(param1);
        catalogRepo.save(param2);
        catalogRepo.save(param3);
        Assertions.assertDoesNotThrow(()->catalogRepo.findAll());
        catalogRepo.save(param4);
        Assertions.assertThrows(DataIntegrityViolationException.class,()->catalogRepo.findAll());
    }



}
