package org.jnd.microservices.turbine;

/**
 * Created by justin on 25/10/2015.
 */
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@EnableTurbine
@EnableDiscoveryClient
public class TurbineApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(TurbineApplication.class).web(true).run(args);
    }
}