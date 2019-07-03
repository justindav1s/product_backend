package org.jnd.microservices.apigateway;

import org.jnd.microservices.apigateway.controller.GatewayController;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:config.${spring.profiles.active}.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:/config/config.${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class GatewayApplication extends SpringBootServletInitializer  {

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    @Value( "${basket.host}" )
    String basket_host;

    @Value( "${user.host}" )
    String user_host;

    @Value( "${inventory.host}" )
    String inventory_host;

    @Value( "${greeting}" )
    String greeting;

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

    @PostConstruct
    public void debug() {
        log.info("Inventory host : "+inventory_host);
        log.info("Basket host : "+basket_host);
        log.info("User host : "+user_host);
        log.info("Greeting : "+greeting);
    }

}
