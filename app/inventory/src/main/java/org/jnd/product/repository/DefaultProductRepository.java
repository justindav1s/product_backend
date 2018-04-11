package org.jnd.product.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;



@Component("ProductRepository")
@Profile("dev")
public class DefaultProductRepository extends RepositoryBase {

    private Log log = LogFactory.getLog(DefaultProductRepository.class);


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
