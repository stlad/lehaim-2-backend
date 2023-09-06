package ru.vaganov.ResourceServer.JpaTests;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.services.CatalogService;

import java.sql.SQLException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CatalogJpaTests {

    @Autowired
    private CatalogRepo catalogRepo;

    @Test
    public void cannotSaveParamWithSimilarNameAndAddName(){
        Parameter param1 = Parameter.builder().name("Лейкоциты").additionalName("WBC").build();
        Parameter param2 = Parameter.builder().name("Лейкоциты").additionalName("AAA").build();
        Parameter param3 = Parameter.builder().name("Тромбоциты").additionalName("WBC").build();
        Parameter param4 = Parameter.builder().name("Лейкоциты").additionalName("WBC").build();

        catalogRepo.save(param1);
        catalogRepo.save(param2);
        catalogRepo.save(param3);
        Assertions.assertDoesNotThrow(()->catalogRepo.findAll());
        catalogRepo.save(param4);
        Assertions.assertThrows(DataIntegrityViolationException.class,()->catalogRepo.findAll());
    }



}
