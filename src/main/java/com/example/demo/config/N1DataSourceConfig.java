package com.example.demo.config;

import com.example.demo.domain.CarEntity;
import com.example.demo.repo.CarRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {CarRepository.class},
        entityManagerFactoryRef = "n1EntityManagerFactory",
        transactionManagerRef = "n1TransactionManager"
)
@RequiredArgsConstructor
@EnableTransactionManagement
public class N1DataSourceConfig {

    @Primary
    @Bean
    public DataSource n1DataSource() {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("postgres");
        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/n1");
        hikariDataSource.setMaximumPoolSize(10);
        return hikariDataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean n1EntityManagerFactory(EntityManagerFactoryBuilder builder) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put("hibernate.globally_quoted_identifiers", true);

        return builder
                .dataSource(n1DataSource())
                .persistenceUnit("n1")
                .packages(
                        CarEntity.class.getPackage().getName()
                )
                .properties(properties)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager n1TransactionManager(
            final @Qualifier("n1EntityManagerFactory") LocalContainerEntityManagerFactoryBean n1EntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(n1EntityManagerFactory.getObject()));
    }
}
