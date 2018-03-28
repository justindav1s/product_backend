package org.jnd.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@ComponentScan
@SpringBootApplication(scanBasePackages={"org.jnd"})
@RestController
@Configuration
@EnableAutoConfiguration
public class ProductApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProductApplication.class);
        Properties p = new Properties();
        p.setProperty("logging.level.org.springframework", "INFO");
        app.setDefaultProperties(p);

        app.run(ProductApplication.class, args);
    }
}
