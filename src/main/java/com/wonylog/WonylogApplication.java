package com.wonylog;

import com.wonylog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class WonylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WonylogApplication.class, args);
    }

}
