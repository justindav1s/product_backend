package org.jnd.microservices.GitHubWebHookManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ComponentScan
@SpringBootApplication(scanBasePackages={"org.jnd"})
@RestController
@Configuration
@EnableAutoConfiguration
public class GitHubWebHookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubWebHookManagerApplication.class, args);
	}
}
