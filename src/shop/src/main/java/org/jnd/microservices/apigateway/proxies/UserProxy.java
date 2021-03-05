package org.jnd.microservices.apigateway.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;


@Component("UserProxy")
public class UserProxy {

    private Log log = LogFactory.getLog(UserProxy.class);

    @Value( "${user.host}" )
    String user_host;

    private RestTemplate restTemplate = new RestTemplate();;

    public ResponseEntity<String> login(User user, HttpHeaders headers) {

        log.debug("UserProxy login START");

        log.debug("UserProxy login : "+user);
        log.debug("http://"+ user_host +"/user/login");
        for (String header : headers.keySet())   {
            log.debug("UserProxy header : "+header + " val : "+ headers.get(header));
        }

        HttpEntity<String> request =  null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug("User : "+mapper.writeValueAsString(user)); 
            request = new HttpEntity<>(mapper.writeValueAsString(user), headers);   
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<User> exchange = null;
        try {
            exchange =
                    this.restTemplate.exchange(
                            "http://" + user_host + "/user/login",
                            HttpMethod.POST,
                            request,
                            new ParameterizedTypeReference<User>() {});
                            
        }
        catch (Exception e) {
            e.printStackTrace();
            //return new ResponseEntity<>(null, null, HttpStatus.SERVICE_UNAVAILABLE);
        }

        user = exchange.getBody();
        log.debug("UserProxy login Response : "+user);
        ResponseEntity<String> resp = null;
        try {
            log.debug("exchange.getBody() : "+mapper.writeValueAsString(exchange.getBody())); 
            resp = new ResponseEntity<>(mapper.writeValueAsString(exchange.getBody()), null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("UserProxy login END");
        return resp;
    }


    public String logout(int id, HttpHeaders headers) {
        log.debug("UserProxy logout basket id : "+id);
        restTemplate.delete("http://"+ user_host +"/user/logout/"+id);
        return "LOGGED OUT";
    }
}
