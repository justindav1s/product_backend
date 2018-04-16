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
        ResponseEntity<User> response = userProxy.login(user, headers);
        return response;
    }

    @RequestMapping(value = "/logout/{id}", method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity<?> logout(@PathVariable int id, @RequestHeader HttpHeaders headers) {

        String response = userProxy.logout(id, headers);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/basket/get/{basketid}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getBasket(@PathVariable int basketid, @RequestHeader HttpHeaders headers) {
        ResponseEntity<Basket> response = basketProxy.getBasket(basketid, headers);
        return response;
    }

    @RequestMapping(value = "/basket/{basketid}/add/{productid}", method = RequestMethod.PUT, produces = "application/json")
    ResponseEntity<?> addToBasket(@PathVariable int basketid, @PathVariable int productid, @RequestHeader HttpHeaders headers) {

        ResponseEntity<Basket> response = basketProxy.addToBasket(basketid, productid, headers);
        return response;
    }

    @RequestMapping(value = "/basket/{basketid}/remove/{itemAtIndex}", method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity<?> removeFromBasket(@PathVariable int basketid, @PathVariable int itemAtIndex, @RequestHeader HttpHeaders headers) {

        ResponseEntity<Basket> response = basketProxy.removefromBasket(basketid, itemAtIndex, headers);
        return response;
    }

    @RequestMapping(value = "/basket/{basketid}/empty", method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity<?> emptyBasket(@PathVariable int basketid, @RequestHeader HttpHeaders headers) {

        ResponseEntity<Basket> response = basketProxy.emptyBasket(basketid, headers);
        return response;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getProduct(@PathVariable int id, @RequestHeader HttpHeaders headers) {

        return inventoryProxy.getProduct(String.valueOf(id), headers);;
    }

    @RequestMapping(value = "/products/types", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getProductTypes(@RequestHeader HttpHeaders headers) {

        log.debug("BEFORE B3Headers");
        this.getB3Headers(headers);

        return inventoryProxy.getProductTypes(headers);
    }

    @RequestMapping(value = "/products/all", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getAllProducts(@RequestHeader HttpHeaders headers) {

        return inventoryProxy.getAllProducts(headers);
    }

    @RequestMapping(value = "/products/type/{type}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getAllProductsOfType(@PathVariable String type, @RequestHeader HttpHeaders headers) {

        return inventoryProxy.getProductsofType(type, headers);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }

    public HttpHeaders getB3Headers(HttpHeaders headers)   {
        HttpHeaders b3Headers = new HttpHeaders();

        log.debug("x-request-id : " + headers.get("x-request-id"));
        log.debug("x-b3-traceid : " + headers.get("x-b3-traceid"));
        log.debug("x-b3-spanid : " + headers.get("x-b3-spanid"));
        log.debug("x-b3-parentspanid : " + headers.get("x-b3-parentspanid"));
        log.debug("x-b3-sampled : " + headers.get("x-b3-sampled"));
        log.debug("x-b3-flags : " + headers.get("x-b3-flags"));
        log.debug("x-ot-span-context : " + headers.get("x-ot-span-context"));

        b3Headers.put("x-request-id", headers.get("x-request-id"));
        b3Headers.put("x-b3-traceid", headers.get("x-b3-traceid"));
        b3Headers.put("x-b3-spanid", headers.get("x-b3-spanid"));
        b3Headers.put("x-b3-parentspanid", headers.get("x-b3-parentspanid"));
        b3Headers.put("x-b3-sampled", headers.get("x-b3-sampled"));
        b3Headers.put("x-b3-flags", headers.get("x-b3-flags"));
        b3Headers.put("x-ot-span-context", headers.get("x-ot-span-context"));

        return b3Headers;
    }
}
