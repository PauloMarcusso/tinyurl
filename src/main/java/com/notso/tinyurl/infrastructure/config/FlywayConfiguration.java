package com.notso.tinyurl.infrastructure.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class FlywayConfiguration {

//  @Bean(initMethod = "migrate")
//  public Flyway flyway(DataSource dataSource, FlywayProperties properties) {
//    return Flyway.configure()
//        .dataSource(dataSource)
//        .locations(properties.getLocations())
//        .baselineOnMigrate(true)
//        .load();
//  }

}
