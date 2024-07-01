package com.where.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.config.location=classpath:./application-test.yml")
@ActiveProfiles(value = "test")
class WhereApplicationTests {
    @Test
    void contextLoads() {
    }
}
