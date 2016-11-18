package com.rubix.transformer.startup;

import com.rubix.transformer.constants.ApplicationConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by sudhir.m on 11/11/16.
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ApplicationConstants.SERVICE_TRANSFORMER_PACKAGE, ApplicationConstants.SERVICE_COMMON_PACKAGE})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


}
