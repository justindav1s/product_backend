package org.jnd.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class BasketApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasketApplication.class, args);
    }

    @RequestMapping("/")
    public String ping() {
        return BasketApplication.class+" UP\n";
    }
}
