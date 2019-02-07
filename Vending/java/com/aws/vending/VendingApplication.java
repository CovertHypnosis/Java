package com.aws.vending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.aws"})
public class VendingApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingApplication.class, args);
    }

}

