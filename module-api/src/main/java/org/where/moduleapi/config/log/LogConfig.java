package org.where.moduleapi.config.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Bean
    public ApiLoggingFilter apiLoggingFilter() {
        return new ApiLoggingFilter();
    }
}
