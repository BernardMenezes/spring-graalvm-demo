package com.example.demo.config;

import com.example.demo.domain.CarEntity;
import com.example.demo.domain.RevisionEntity;
import com.example.demo.repo.CarRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
        basePackageClasses = {},
        entityManagerFactoryRef = "n2EntityManagerFactory",
        transactionManagerRef = "n2TransactionManager"
)
@RequiredArgsConstructor
@EnableTransactionManagement
public class N2DataSourceConfig {

    @Bean
    public DataSource n2DataSource() {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("postgres");
        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/n2");
        hikariDataSource.setMaximumPoolSize(10);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean n2EntityManagerFactory(EntityManagerFactoryBuilder builder) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put("hibernate.globally_quoted_identifiers", true);

        return builder
                .dataSource(n2DataSource())
                .persistenceUnit("n2")
                .packages(
                        RevisionEntity.class.getPackage().getName()
                )
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager n2TransactionManager(
            final @Qualifier("n1EntityManagerFactory") LocalContainerEntityManagerFactoryBean n1EntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(n1EntityManagerFactory.getObject()));
    }
}
