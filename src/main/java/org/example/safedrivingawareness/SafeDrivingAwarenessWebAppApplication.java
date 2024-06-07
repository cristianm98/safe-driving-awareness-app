package org.example.safedrivingawareness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SafeDrivingAwarenessWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeDrivingAwarenessWebAppApplication.class, args);
    }
}
