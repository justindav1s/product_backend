package org.jnd.product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.product.controller.ProductController;
import org.jnd.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class ProductApplicationTests {

	private Log log = LogFactory.getLog(ProductController.class);

	@Autowired
	private ProductRepository repository;

	@Test
	public void loadAll() {
		log.debug("TEST : loadAll");
		assertTrue(repository.getProducts().size() == 18);
		log.debug("TEST : Product count : "+repository.getProducts().size());
	}
}
