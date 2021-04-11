package com.delivery.presenter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.delivery.database.entities"})
@EnableJpaRepositories(basePackages = {"com.delivery.database.repositories"})
public class DBConfig {
}
