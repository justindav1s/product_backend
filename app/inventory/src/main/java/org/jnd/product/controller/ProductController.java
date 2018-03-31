package org.jnd.product.controller;

import org.jnd.microservices.model.ProductType;
import org.jnd.product.repository.ProductRepository;
import org.jnd.microservices.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.swing.text.html.HTML;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private Log log = LogFactory.getLog(ProductController.class);

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    public void init()  {

        log.debug("TESTING : Setting up repository");

        if(repository.size() == 0) {
            repository.put("1", new Product("1", "marmalade", ProductType.FOOD, 1.29f, 12));
            repository.put("2", new Product("2", "milk", ProductType.FOOD, 0.83f, 20));
            repository.put("3", new Product("3", "baked beans", ProductType.FOOD, 0.79f, 23));
            repository.put("4", new Product("4", "bread", ProductType.FOOD, 1.09f, 80));
            repository.put("5", new Product("5", "beef steak", ProductType.FOOD, 6.99f, 5));
            repository.put("6", new Product("6", "chicken", ProductType.FOOD, 3.99f, 8));
            repository.put("7", new Product("7", "coffee", ProductType.FOOD, 3.39f, 12));
            repository.put("8", new Product("8", "tea", ProductType.FOOD, 1.29f, 72));
            repository.put("9", new Product("9", "biscuits", ProductType.FOOD, 0.79f, 23));
            repository.put("10", new Product("10", "cake", ProductType.FOOD, 2.79f, 15));

            repository.put("11", new Product("11", "socks", ProductType.CLOTHES, 3.39f, 20));
            repository.put("12", new Product("12", "jacket", ProductType.CLOTHES, 49.99f, 5));
            repository.put("13", new Product("13", "shoes", ProductType.CLOTHES, 59.99f, 10));
            repository.put("14", new Product("14", "hat", ProductType.CLOTHES, 20.99f, 2));

            repository.put("15", new Product("15", "camera", ProductType.GADGET, 43.39f, 20));
            repository.put("16", new Product("16", "tv", ProductType.GADGET, 499.99f, 5));
            repository.put("17", new Product("17", "iPad", ProductType.GADGET, 599.99f, 10));
            repository.put("18", new Product("18", "Robot", ProductType.GADGET, 99.99f, 2));
        }

    }



    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Object[]> getAll(@RequestHeader HttpHeaders headers) {

        log.debug("Product get All");

        Object[] products = repository.values().toArray();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Object[]> getTypes(@RequestHeader HttpHeaders headers) {

        log.debug("Product get types");

        Object[] types = ProductType.values();

        return new ResponseEntity<>(types, HttpStatus.OK);
    }


    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Product[]> getProductsOfType(@PathVariable String type, @RequestHeader HttpHeaders headers) {

        log.debug("Product get of type :"+type);
        ArrayList products = new ArrayList();
        for (Product p : repository.values()){
            if (p.getType().toString().equalsIgnoreCase(type))   {
                products.add(p);
            }
        };

        return new ResponseEntity(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Product> get(@PathVariable Integer productId, @RequestHeader HttpHeaders headers) {


        log.debug("Product get : "+productId);

        Product product = repository.get(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/create/{productName}/{productType}/{price}", method = RequestMethod.POST, produces = "application/json")
    ResponseEntity<Object[]> create(@PathVariable String productName,
                                    @PathVariable String productType,
                                    @PathVariable Float productPrice,
                                    @RequestHeader HttpHeaders headers) {

        log.debug("Product Create : "+productName);

        int productCount = repository.values().toArray().length;
        int productId = productCount++;

        ProductType type = null;
        if (productType.equalsIgnoreCase(ProductType.CLOTHES.toString()))    {
            type = ProductType.CLOTHES;
        }
        else if (productType.equalsIgnoreCase(ProductType.FOOD.toString()))    {
            type = ProductType.FOOD;
        }
        else if (productType.equalsIgnoreCase(ProductType.GADGET.toString()))    {
            type = ProductType.GADGET;
        }

        Product p = new Product(Integer.toString(productId), productName, type, productPrice, 0);

        repository.put(Integer.toString(productId), p);

        log.debug("Product Created : "+p);

        Object[] products = repository.values().toArray();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
