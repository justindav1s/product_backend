package org.jnd.user.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component("UserProxy")
public class UserProxy {

    private Log log = LogFactory.getLog(UserProxy.class);

    @Value( "${user.host}" )
    String user_host;

    private RestTemplate restTemplate = new RestTemplate();;

    public User login(User user) {

        log.debug("UserProxy login : "+user);

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity<User> exchange =
                this.restTemplate.exchange(
                        "http://"+ user_host +"/user/login",
                        HttpMethod.POST,
                        request,
                        User.class);

        user = exchange.getBody();
        log.debug("UserProxy login Response : "+user);

        if (user == null)
            throw new RuntimeException();

        return user;
    }


    public String logout(int id) {
        log.debug("UserProxy logout user id : "+id);
        restTemplate.delete("http://"+ user_host +"/user/logout/"+id);
        return "LOGGED OUT";
    }
}
