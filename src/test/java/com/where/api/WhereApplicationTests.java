package com.where.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = "spring.config.location=classpath:./application-test.yml")
@ActiveProfiles(value = "test")
class WhereApplicationTests {
    @Test
    void contextLoads() {
    }
}
