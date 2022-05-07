package com.example.yongbread.testcontainer;


import com.example.yongbread.repository.ProductJdbcRepository;
import com.example.yongbread.repository.ProductRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@DirtiesContext
@SpringJUnitConfig
public class AbstractContainerDatabaseTest {
    @Container
    private static final MySQLContainer mysqlContainer = (MySQLContainer)
            new MySQLContainer("mysql:8.0.19")
                    .withInitScript("schema.sql");

    @Configuration
    @ComponentScan(basePackages = "com.example.yongbread")
    static class Config {

        @Bean
        public DataSource dataSource() {
            mysqlContainer.start();

            return DataSourceBuilder.create()
                    .driverClassName(mysqlContainer.getDriverClassName())
                    .url(mysqlContainer.getJdbcUrl())
                    .username(mysqlContainer.getUsername())
                    .password(mysqlContainer.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public ProductRepository productRepository(
                NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new ProductJdbcRepository(namedParameterJdbcTemplate);
        }
    }
}
