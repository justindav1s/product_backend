package org.jnd.microservices.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@ComponentScan
@SpringBootApplication(scanBasePackages={"org.jnd"})
@PropertySources({
        @PropertySource("config.default.properties"),
        @PropertySource(value = "file:/config/config.${spring.profiles.active:default}.properties", ignoreResourceNotFound = true)
})
@RestController
@Configuration
@EnableAutoConfiguration
public class ProductApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "OK";
    }
}
