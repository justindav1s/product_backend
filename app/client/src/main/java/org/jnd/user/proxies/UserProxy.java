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


@Component("UserProxy")
public class UserProxy {

    private Log log = LogFactory.getLog(UserProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public User login(User user) {

        log.debug("UserProxy login : "+user);

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity<User> exchange =
                this.restTemplate.exchange(
                        "http://istio-ingress.istio-system.svc/user/login",
                        HttpMethod.POST,
                        request,
                        User.class);

        user = exchange.getBody();
        log.debug("UserProxy login Response : "+user);

        if (user == null)
            throw new RuntimeException();

        return user;
    }


    public User logout(User user) {

        log.debug("UserProxy logout : "+user);
        restTemplate.delete("http://istio-ingress.istio-system.svc/user/logout/{id}", user.getId());

        return user;
    }
}
