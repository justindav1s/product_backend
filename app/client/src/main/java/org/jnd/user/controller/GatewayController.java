package org.jnd.user.controller;

import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.User;
import org.jnd.user.proxies.BasketProxy;
import org.jnd.user.proxies.InventoryProxy;
import org.jnd.user.proxies.UserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * ${environment.user_backend}/user/login POST
 * ${environment.user_backend}/user/logout DELETE
 * ${environment.basket_backend}/basket/get/${basket.id} GET
 * ${environment.basket_backend}/basket/add/${product.id} PUT
 * ${environment.basket_backend}/basket/remove/${itemAtIndex} DELETE
 * ${environment.inventory_backend}/products/types GET
 * ${environment.inventory_backend}/products/all GET
 * ${environment.inventory_backend}/products/type/${type} GET
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class GatewayController {

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private InventoryProxy inventoryProxy;

    @Autowired
    private BasketProxy basketProxy;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    ResponseEntity<?> login(@RequestBody User user, @RequestHeader HttpHeaders headers) {
        user = userProxy.login(user);;
        return new ResponseEntity<>(user, null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/logout/{id}", method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity<?> logout(@PathVariable int id, @RequestHeader HttpHeaders headers) {

        String response = userProxy.logout(id);
        return new ResponseEntity<>(response, null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/basket/get/{basketid}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getBasket(@PathVariable int basketid, @RequestHeader HttpHeaders headers) {
        Basket basket = basketProxy.getBasket(basketid);
        return new ResponseEntity<>(basket, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/basket/add/{productid}", method = RequestMethod.PUT, produces = "application/json")
    ResponseEntity<?> addToBasket(@PathVariable int productid, @RequestHeader HttpHeaders headers) {

        return new ResponseEntity<>("OK", null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/basket/remove/{itemAtIndex}", method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity<?> removeFromBasket(@PathVariable int index, @RequestHeader HttpHeaders headers) {

        return new ResponseEntity<>("OK", null, HttpStatus.GONE);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getProduct(@PathVariable int id, @RequestHeader HttpHeaders headers) {

        Product p = inventoryProxy.getProduct(String.valueOf(id));
        return new ResponseEntity<>(p, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/types", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getProductTypes(@RequestHeader HttpHeaders headers) {

        List<String> types = inventoryProxy.getProductTypes();
        return new ResponseEntity<>(types, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/all", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getAllProducts(@RequestHeader HttpHeaders headers) {

        List<Product> products = inventoryProxy.getAllProducts();
        return new ResponseEntity<>(products, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/type/{type}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getAllProductsOfType(@PathVariable String type, @RequestHeader HttpHeaders headers) {

        List<Product> products = inventoryProxy.getProductsofType(type);
        return new ResponseEntity<>(products, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }
}
