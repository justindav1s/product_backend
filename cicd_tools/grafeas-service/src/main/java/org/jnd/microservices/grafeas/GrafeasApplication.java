package org.jnd.microservices.grafeas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@RestController
public class GrafeasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrafeasApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "GRAFEAS";
	}
}
