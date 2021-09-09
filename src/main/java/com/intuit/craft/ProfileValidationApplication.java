package com.intuit.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Profile validation spring boot application.
 */
@EnableScheduling
@SpringBootApplication
public class ProfileValidationApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProfileValidationApplication.class, args);
    }

}
