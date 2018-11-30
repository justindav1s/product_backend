package org.jnd.microservices.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Autowired
	private MongoTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@PostConstruct
	public void connect() {
		System.out.println("PostConstruct");
		Set<String> collections = template.getCollectionNames();
		for (String c : collections)	{
			System.out.println("collection : "+c);
		}
	}
}
