package ru.vaganov.lehaim;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vaganov.lehaim.config.TestConfig;

@SpringBootTest(classes = TestConfig.class)
@Testcontainers
class BaseContextTest {

    @Test
    void setUp(){

    }
}
