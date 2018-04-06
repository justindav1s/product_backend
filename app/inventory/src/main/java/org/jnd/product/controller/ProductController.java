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

            repository.put("1", new Product("1", "marmalade", ProductType.FOOD, 1.29f));
            repository.put("2", new Product("2", "milk", ProductType.FOOD, 0.83f));
            repository.put("3", new Product("3", "chocolate", ProductType.FOOD, 0.79f));
            repository.put("4", new Product("4", "bread", ProductType.FOOD, 1.09f));
            repository.put("5", new Product("5", "broccoli", ProductType.FOOD, 0.99f));
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



    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Object[]> getAll(@RequestHeader HttpHeaders headers) {

        log.debug("Product get All");

        Object[] products = repository.values().toArray();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<String[]> getTypes(@RequestHeader HttpHeaders headers) {

        log.debug("Product get types");

        ArrayList types = new ArrayList();
        for (ProductType pt : ProductType.values()){
            types.add(pt.toString());
        }

        return new ResponseEntity(types, HttpStatus.OK);
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

        Product product = repository.get(Integer.toString(productId));

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
        else if (productType.equalsIgnoreCase(ProductType.GADGETS.toString()))    {
            type = ProductType.GADGETS;
        }

        Product p = new Product(Integer.toString(productId), productName, type, productPrice);

        repository.put(Integer.toString(productId), p);

        log.debug("Product Created : "+p);

        Object[] products = repository.values().toArray();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
