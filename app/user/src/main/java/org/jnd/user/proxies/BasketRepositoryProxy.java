package org.jnd.user.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("BasketRepositoryProxy")
public class BasketRepositoryProxy {

    private Log log = LogFactory.getLog(BasketRepositoryProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public User getBasket(User user) {

        log.debug("BasketRepositoryProxy getBasket for user : "+user);

        HttpEntity<User> request = new HttpEntity<>(user);
        //Basket basket = restTemplate.postForObject("http://basket:8080/basket/create", request, Basket.class);

        ResponseEntity<User> exchange =
                this.restTemplate.exchange(
                        "http://basket:8080/basket/create",
                        HttpMethod.POST,
                        request,
                        User.class);

        user = exchange.getBody();
        log.debug("Basket Response : "+user);

        if (user == null)
            throw new RuntimeException();

        return user;
    }

}
