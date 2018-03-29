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


@RestController
@RequestMapping("/basket")
public class BasketController {

    private static final Logger log = LoggerFactory.getLogger(BasketController.class);

    @Autowired
    private ProductRepositoryProxy productrepository;

    @Autowired
    private BasketRepository basketRepository;

    String[] keys = { "keycloak_name", "keycloak_email", "x-forwarded-for", "keycloak_username", "keycloak_subject" };

    @RequestMapping(value = "/create/{basketId}", method = RequestMethod.PUT)
    ResponseEntity<?> create(@PathVariable String basketId, @RequestHeader HttpHeaders headers) {

        String[] args = { BasketController.class.getName(), "create", "basket", basketId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Create");

        //log.debug("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, basketId);
        Basket basket = new Basket(basketId);
        basketRepository.put(basketId, basket);
        basket = basketRepository.get(basketId);
        return new ResponseEntity<>(basket, null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/remove/{basketId}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable String basketId, @RequestHeader HttpHeaders headers)    {

        String[] args = { BasketController.class.getName(), "remove", "basket", basketId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Remove Basket#"+basketId);
//        basketRepository.delete(basketId);
        return new ResponseEntity<>(null, null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/clearall", method = RequestMethod.DELETE)
    ResponseEntity<?> clearall(@RequestHeader HttpHeaders headers)    {

        String[] args = { BasketController.class.getName(), "clearall", "basket" };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Clearing all Baskets");
//        basketRepository.deleteAll();
        return new ResponseEntity<>(null, null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/{basketId}/add/{productId}", method = RequestMethod.PUT)
    ResponseEntity<Basket> add(@PathVariable String basketId, @PathVariable String productId, @RequestHeader HttpHeaders headers) {


        String[] args = { BasketController.class.getName(), "add", "basket", basketId, productId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Basket #"+basketId+" Add Product#"+productId);

        Product product = productrepository.getProduct(productId);
        Basket basket = basketRepository.get(basketId);
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
    ResponseEntity<Basket> remove(@PathVariable String basketId, @PathVariable String productId, @RequestHeader HttpHeaders headers) {

        String[] args = { BasketController.class.getName(), "remove", "basket", basketId, productId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Basket #"+basketId+" Add Product#"+productId);

        Product product = productrepository.getProduct(productId);
        Basket basket = basketRepository.get(basketId);
        if (basket.getProducts() != null) {
            basket.getProducts().remove(product);
        }
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{basketId}/empty", method = RequestMethod.POST)
    ResponseEntity<Basket> empty(@PathVariable String basketId, @RequestHeader HttpHeaders headers) {

        String[] args = { BasketController.class.getName(), "empty", "basket", basketId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));

        log.debug("Basket #"+basketId+" Emptying");

        Basket basket = basketRepository.get(basketId);
        if (basket.getProducts() != null) {
            basket.getProducts().clear();
        }
        basket = basketRepository.get(basketId);
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{basketId}", method = RequestMethod.GET)
    ResponseEntity<Basket>  get(@PathVariable String basketId, @RequestHeader HttpHeaders headers) {

        String[] args = { BasketController.class.getName(), "get", "basket", basketId };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));


        log.debug("Get basket : "+basketId);

        Basket basket = basketRepository.get(basketId);

        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ResponseEntity<Object>  list(@RequestHeader HttpHeaders headers) {

        String[] args = { BasketController.class.getName(), "list", "basket" };
        log.debug(InfoLineBuilder.getLine(args, headers, keys));


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
