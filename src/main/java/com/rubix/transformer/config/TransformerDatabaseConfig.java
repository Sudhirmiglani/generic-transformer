package com.rubix.transformer.config;

import com.rubix.transformer.constants.ApplicationConstants;
import com.rubix.wms.common.constants.DatabaseConstants;
import com.rubix.wms.common.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by sudhir.m on 20/10/16.
 */


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = ApplicationConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = ApplicationConstants.TRANSACTION_MANAGER,
        basePackages = {ApplicationConstants.SERVICE_TRANSFORMER_PACKAGE}
)
public class TransformerDatabaseConfig {

    @Autowired
    private ConfigUtil configUtil;

    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = ApplicationConstants.DATASOURCE)
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(configUtil.getAsString(DatabaseConstants.PROPERTY_DB_DRIVER, ApplicationConstants.SERVICE_NAME));
        dataSource.setUrl(configUtil.getAsString(DatabaseConstants.PROPERTY_DB_URL, ApplicationConstants.SERVICE_NAME));
        dataSource.setUsername(configUtil.getAsString(DatabaseConstants.PROPERTY_DB_USERNAME, ApplicationConstants.SERVICE_NAME));
        dataSource.setPassword(configUtil.getAsString(DatabaseConstants.PROPERTY_DB_PASSWORD, ApplicationConstants.SERVICE_NAME));
        return dataSource;
    }

    @Bean(name = ApplicationConstants.ENTITY_MANAGER_FACTORY)
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan(configUtil.getAsString(DatabaseConstants.PACKAGES_TO_SCAN, ApplicationConstants.SERVICE_NAME));
        lef.setPersistenceUnitName(configUtil.getAsString(DatabaseConstants.HIBERNATE_UNIT_NAME, ApplicationConstants.SERVICE_NAME));
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean(name = ApplicationConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean(name = ApplicationConstants.ENTITY_MANAGER)
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }
}


