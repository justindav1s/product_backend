package org.jnd.product.repository.v1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.ProductType;
import org.jnd.product.repository.ProductCache;
import org.jnd.product.repository.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component("ProductRepository")
@Profile("v1")
public class ProductRepository extends RepositoryBase {

    private Log log = LogFactory.getLog(ProductRepository.class);

    @Autowired
    private ProductCache cache;

    @PostConstruct
    public void init()  {

        log.debug("Setting up repository");
        getTypes().add(ProductType.FOOD.toString());
        getProducts().putAll(cache.getFood());

    }
}
