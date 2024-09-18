package org.where.modulewebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("org.where.modulecore")
@EnableJpaRepositories("org.where.modulecore")
@SpringBootApplication(scanBasePackages = "org.where")
public class ModuleWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleWebsocketApplication.class, args);
    }

}
