package org.jnd.product.repository.v3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.ProductType;
import org.jnd.product.repository.ProductCache;
import org.jnd.product.repository.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;


@Component("ProductRepository")
@Profile("v3")
public class ProductRepository extends RepositoryBase {

    private Log log = LogFactory.getLog(ProductRepository.class);

    @Autowired
    private ProductCache cache;

    @PostConstruct
    public void init()  {

        log.debug("TESTING : Setting up repository");

        if(getTypes().size() == 0) {
            getTypes().add(ProductType.FOOD.toString());
            getTypes().add(ProductType.CLOTHES.toString());
            getTypes().add(ProductType.GADGETS.toString());
        }

        if(getProducts().size() == 0) {
            getProducts().putAll(cache.getFood());
            getProducts().putAll(cache.getClothes());
            getProducts().putAll(cache.getGadgets());
        }

    }

}
