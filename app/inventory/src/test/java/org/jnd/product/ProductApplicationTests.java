package org.jnd.product;

import org.jnd.microservices.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.ProductType;
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
			repository.put("1", new Product("1", "marmalade", ProductType.FOOD, 1.29f));
			repository.put("2", new Product("2", "milk", ProductType.FOOD, 0.83f));
			repository.put("3", new Product("3", "baked beans", ProductType.FOOD, 0.79f));
			repository.put("4", new Product("4", "bread", ProductType.FOOD, 1.09f));
			repository.put("5", new Product("5", "beef steak", ProductType.FOOD, 6.99f));
			repository.put("6", new Product("6", "chicken", ProductType.FOOD, 3.99f));
			repository.put("7", new Product("7", "coffee", ProductType.FOOD, 3.39f));
			repository.put("8", new Product("8", "tea", ProductType.FOOD, 1.29f));
			repository.put("9", new Product("9", "biscuits", ProductType.FOOD, 0.79f));
			repository.put("10", new Product("10", "cake", ProductType.FOOD, 2.79f));

			repository.put("11", new Product("11", "socks", ProductType.CLOTHES, 3.39f));
			repository.put("12", new Product("12", "jacket", ProductType.CLOTHES, 49.99f));
			repository.put("13", new Product("13", "shoes", ProductType.CLOTHES, 59.99f));
			repository.put("14", new Product("14", "hat", ProductType.CLOTHES, 20.99f));

			repository.put("15", new Product("15", "camera", ProductType.GADGETS, 43.39f));
			repository.put("16", new Product("16", "tv", ProductType.GADGETS, 499.99f));
			repository.put("17", new Product("17", "iPad", ProductType.GADGETS, 599.99f));
			repository.put("18", new Product("18", "Robot", ProductType.GADGETS, 99.99f));
		}

	}

	@Autowired
	private ProductRepository repository;


	@Test
	public void loadAll() {
		log.debug("TEST : loadAll");
		assertTrue(repository.size() == 18);
		log.debug("TEST : Product count : "+repository.size());
		repository.clear();
	}
}
