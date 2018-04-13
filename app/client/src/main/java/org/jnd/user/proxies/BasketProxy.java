package org.jnd.user.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("BasketProxy")
public class BasketProxy {

    private Log log = LogFactory.getLog(BasketProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public Basket addToBasket(User user, Product product) {

        return null;
    }

    public Basket removefromBasket(User user, Product product) {

        return null;
    }
}
