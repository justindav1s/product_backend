package org.jnd.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@PropertySources({
        @PropertySource("config.default.properties"),
        @PropertySource(value = "file:/config/config.${spring.profiles.active:default}.properties", ignoreResourceNotFound = true)
})
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class GatewayApplication extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
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
