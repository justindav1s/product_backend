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
public class DefaultProductRepository implements ProductRepository {

    private Log log = LogFactory.getLog(DefaultProductRepository.class);
    private HashMap<String, Product> products = new HashMap<String, Product>();
    private ArrayList<String> types = new ArrayList<String>();

    @Autowired
    private ProductCache cache;

    @PostConstruct
    public void init()  {

        log.debug("TESTING : Setting up repository");

        if(types.size() == 0) {
            types.add(ProductType.FOOD.toString());
            types.add(ProductType.CLOTHES.toString());
            types.add(ProductType.GADGETS.toString());
        }

        if(products.size() == 0) {
            products.putAll(cache.getFood());
            products.putAll(cache.getClothes());
            products.putAll(cache.getGadgets());
        }

    }

    @Override
    public HashMap<String, Product> getProducts() {
        return products;
    }

    @Override
    public ArrayList<String> getTypes() {
        return types;
    }
}
