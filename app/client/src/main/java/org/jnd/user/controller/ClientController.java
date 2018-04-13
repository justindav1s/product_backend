package org.jnd.user.controller;

import org.jnd.microservices.model.User;
import org.jnd.user.proxies.UserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private UserProxy userProxy;

    @RequestMapping(value = "/test1", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> create(@RequestHeader HttpHeaders headers) {

        User user = userProxy.login(new User("justinndavis", "password"));
        this.sleep(500);


        userProxy.logout(user);
        return new ResponseEntity<>("OK", null, HttpStatus.OK);
    }


    private void sleep(int millis)  {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
