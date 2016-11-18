package com.rubix.transformer.config;

import com.rubix.transformer.constants.ApplicationConstants;
import com.rubix.wms.common.constants.DatabaseConstants;
import com.rubix.wms.common.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Created by sudhir.m on 20/10/16.
 */
@Configuration
public class AppConfig {

    @Autowired
    private ConfigUtil configUtil;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(configUtil.getAsBoolean(DatabaseConstants.SHOW_SQL, ApplicationConstants.SERVICE_NAME));
        jpaVendorAdapter.setGenerateDdl(configUtil.getAsBoolean(DatabaseConstants.GENERATE_DDL, ApplicationConstants.SERVICE_NAME));
        jpaVendorAdapter.setDatabasePlatform(DatabaseConstants.DIALECT);
        return jpaVendorAdapter;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
