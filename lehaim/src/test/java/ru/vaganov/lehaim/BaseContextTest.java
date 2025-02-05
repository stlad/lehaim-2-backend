package ru.vaganov.lehaim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vaganov.lehaim.config.TestConfig;
import ru.vaganov.lehaim.data.TestData;

@SpringBootTest(classes = TestConfig.class)
@Testcontainers
public class BaseContextTest {

    @Autowired
    protected TestData testData;
}
