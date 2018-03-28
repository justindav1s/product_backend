package org.jnd.product;

import org.jnd.microservices.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.product.controller.ProductController;
import org.jnd.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApplicationTests {

	private Log log = LogFactory.getLog(ProductController.class);

	@PostConstruct
	public void init()  {

		repository.clear();

		log.debug("TESTING : Setting up repository");

		if(repository.size() == 0) {
			repository.put("1", new Product("1", "marmalade"));
			repository.put("2", new Product("2", "milk"));
			repository.put("3", new Product("3", "baked beans"));
			repository.put("4", new Product("4", "bread"));
			repository.put("5", new Product("5", "beef"));
			repository.put("6", new Product("6", "chicken"));
			repository.put("7", new Product("7", "coffee"));
			repository.put("8", new Product("8", "tea"));
			repository.put("9", new Product("9", "biscuits"));
			repository.put("10", new Product("10", "cake"));
		}

	}

	@Autowired
	private ProductRepository repository;


	@Test
	public void loadAll() {
		log.debug("TEST : loadAll");
		assertTrue(repository.size() == 10);
		log.debug("TEST : Product count : "+repository.size());
		repository.clear();
	}
}
