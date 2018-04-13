package org.jnd.user.proxies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component("BasketProxy")
public class BasketProxy {

    @Value( "${basket.host}" )
    String basket_host;

    private Log log = LogFactory.getLog(BasketProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public Basket addToBasket(int basketid, int productid) {

        log.debug(">> addToBasket basketid : "+basketid+" productid : "+productid);

        ResponseEntity<Basket> exchange =
                this.restTemplate.exchange(
                        "http://"+basket_host+"/basket/{basketid}/add/{productid}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<Basket>() {},
                        basketid, productid);

        return exchange.getBody();
    }

    public Basket removefromBasket(int basketid, int productindex) {

        log.debug(">> removefromBasket basketid : "+basketid+" productindex : "+productindex);

        ResponseEntity<Basket> exchange =
                this.restTemplate.exchange(
                        "http://"+basket_host+"/basket/{basketid}/remove/{productindex}",
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<Basket>() {},
                        basketid, productindex);

        return exchange.getBody();
    }

    public Basket getBasket(int basketid) {
        Basket basket = restTemplate.getForObject("http://"+basket_host+"/basket/get/"+basketid, Basket.class);

        log.debug("basket response : "+basket);

        if (basket == null)
            throw new RuntimeException();

        return basket;
    }

    public Basket emptyBasket(int basketid) {

        log.debug(">> emptyBasket basketid : "+basketid);

        ResponseEntity<Basket> exchange =
                this.restTemplate.exchange(
                        "http://"+basket_host+"/basket/{basketid}/empty",
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<Basket>() {},
                        basketid);

        return exchange.getBody();
    }
}
