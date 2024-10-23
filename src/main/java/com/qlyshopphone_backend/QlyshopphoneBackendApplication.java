package com.qlyshopphone_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableKafka
public class QlyshopphoneBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QlyshopphoneBackendApplication.class, args);
    }

}
