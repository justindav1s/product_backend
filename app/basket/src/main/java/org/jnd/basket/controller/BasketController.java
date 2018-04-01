package org.jnd.basket.controller;

import org.jnd.basket.proxies.ProductRepositoryProxy;
import org.jnd.basket.repositories.BasketRepository;
import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.trace.InfoLineBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/basket")
public class BasketController {

    private static final Logger log = LoggerFactory.getLogger(BasketController.class);

    @Autowired
    private ProductRepositoryProxy productrepository;

    @Autowired
    private BasketRepository basketRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    ResponseEntity<?> create( @RequestHeader HttpHeaders headers) {

        log.debug("1 Basket Create");

        int basketId = basketRepository.size() + 1;
        Basket basket = new Basket(basketId);
        log.debug("Basket Create #"+basketId);
        log.debug("Basket Create :"+basket);
        basketRepository.put(Integer.toString(basketId), basket);
        log.debug("Basket Repository :"+basketRepository);
        basket = basketRepository.get(Integer.toString(basketId));
        log.debug("Returning Basket :"+basket);
        return new ResponseEntity<>(basket, null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/remove/{basketId}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable int basketId, @RequestHeader HttpHeaders headers)    {

        log.debug("Remove Basket#"+basketId);
        basketRepository.remove(Integer.toString(basketId));
        return new ResponseEntity<>("DELETED", null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/clearall", method = RequestMethod.DELETE)
    ResponseEntity<?> clearall(@RequestHeader HttpHeaders headers)    {

        log.debug("Clearing all Baskets");
        basketRepository.clear();
        return new ResponseEntity<>("CLEAR", null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/{basketId}/add/{productId}", method = RequestMethod.GET)
    ResponseEntity<Basket> add(@PathVariable int basketId, @PathVariable String productId, @RequestHeader HttpHeaders headers) {

        log.debug("Basket #"+basketId+" Add Product#"+productId);

        Product product = productrepository.getProduct(productId);
        Basket basket = basketRepository.get(Integer.toString(basketId));
        if (basket.getProducts() != null) {
            basket.getProducts().add(product);
        }
        else    {
            basket.setProducts(new ArrayList<>());
            basket.getProducts().add(product);
        }
        basket.getProducts().add(product);
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{basketId}/remove/{productId}", method = RequestMethod.DELETE)
    ResponseEntity<Basket> remove(@PathVariable int basketId, @PathVariable String productId, @RequestHeader HttpHeaders headers) {

        log.debug("Basket #"+basketId+" Add Product#"+productId);

        Product product = productrepository.getProduct(productId);
        Basket basket = basketRepository.get(Integer.toString(basketId));
        if (basket.getProducts() != null) {
            basket.getProducts().remove(product);
        }
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{basketId}/empty", method = RequestMethod.POST)
    ResponseEntity<Basket> empty(@PathVariable int basketId, @RequestHeader HttpHeaders headers) {

        log.debug("Basket #"+basketId+" Emptying");

        Basket basket = basketRepository.get(Integer.toString(basketId));
        if (basket.getProducts() != null) {
            basket.getProducts().clear();
        }
        basket = basketRepository.get(basketId);
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{basketId}", method = RequestMethod.GET)
    ResponseEntity<Basket>  get(@PathVariable int basketId, @RequestHeader HttpHeaders headers) {

        log.debug("Get basket : "+basketId);

        Basket basket = basketRepository.get(Integer.toString(basketId));

        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ResponseEntity<Object>  list(@RequestHeader HttpHeaders headers) {

        log.debug("List baskets");

        Object[] baskets = basketRepository.entrySet().toArray();

        return new ResponseEntity<>(baskets, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    ResponseEntity<Object> getInventory() {


        log.debug("Basket inventory #");

        Object inventory = productrepository.getAllProducts();
        log.debug("Basket inventory : "+inventory);

        return new ResponseEntity<>(inventory, null, HttpStatus.OK);
    }

}
