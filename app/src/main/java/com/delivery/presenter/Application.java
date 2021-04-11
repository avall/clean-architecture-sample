package com.delivery.presenter;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.delivery.core.usecases",
    "com.delivery.core.mappers",
    "com.delivery.presenter",
    "com.delivery.presenter.mappers",
    "com.delivery.database"}
    )
public class Application {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
