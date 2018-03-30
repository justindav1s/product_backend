package org.jnd.product.controller;

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

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    private Log log = LogFactory.getLog(ProductController.class);

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    public void init()  {

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

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Object[]> getAll(@RequestHeader HttpHeaders headers) {

        log.debug("Product get All");

        Object[] products = repository.values().toArray();

        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.add("Access-Control-Allow-Origin", "http://localhost:4200");

        return new ResponseEntity<>(products, responseheaders, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Product> get(@PathVariable Integer productId, @RequestHeader HttpHeaders headers) {


        log.debug("Product get : "+productId);

        Product product = repository.get(productId);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.add("Access-Control-Allow-Origin", "http://localhost:4200");
        log.debug("Added Headers "+responseheaders);
        return new ResponseEntity<>(product, responseheaders, HttpStatus.OK);
    }



    @CrossOrigin
    @RequestMapping(value = "/create/{productId}/{productName}", method = RequestMethod.POST, produces = "application/json")
    ResponseEntity<Object[]> create(@PathVariable String productId, @PathVariable String productName, @RequestHeader HttpHeaders headers) {

        Product p = new Product(productId, productName);
        repository.put(productId, p);

        log.debug("Product Create");

        Object[] products = repository.values().toArray();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
