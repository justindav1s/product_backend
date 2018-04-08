package org.jnd.product.repository.v2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.ProductType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;


@Component("ProductRepository")
@Profile("v2")
public class ProductRepository implements org.jnd.product.repository.ProductRepository {

    private Log log = LogFactory.getLog(ProductRepository.class);
    private HashMap<String, Product> products = new HashMap<String, Product>();
    private ArrayList<String> types = new ArrayList<String>();

    @PostConstruct
    public void init()  {

        log.debug("TESTING : Setting up repository");

        if(types.size() == 0) {
            types.add(ProductType.FOOD.toString());
            types.add(ProductType.CLOTHES.toString());
        }

        if(products.size() == 0) {

            products.put("1", new Product("1", "marmalade", ProductType.FOOD, 1.29f));
            products.put("2", new Product("2", "milk", ProductType.FOOD, 0.83f));
            products.put("3", new Product("3", "chocolate", ProductType.FOOD, 0.79f));
            products.put("4", new Product("4", "bread", ProductType.FOOD, 1.09f));
            products.put("5", new Product("5", "broccoli", ProductType.FOOD, 0.99f));
            products.put("6", new Product("6", "chicken", ProductType.FOOD, 3.99f));
            products.put("7", new Product("7", "coffee", ProductType.FOOD, 3.39f));
            products.put("8", new Product("8", "tea", ProductType.FOOD, 1.29f));
            products.put("9", new Product("9", "biscuits", ProductType.FOOD, 0.79f));
            products.put("10", new Product("10", "cake", ProductType.FOOD, 2.79f));

            products.put("11", new Product("11", "socks", ProductType.CLOTHES, 3.39f));
            products.put("12", new Product("12", "jacket", ProductType.CLOTHES, 49.99f));
            products.put("13", new Product("13", "shoes", ProductType.CLOTHES, 59.99f));
            products.put("14", new Product("14", "hat", ProductType.CLOTHES, 20.99f));
        }

    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
}
