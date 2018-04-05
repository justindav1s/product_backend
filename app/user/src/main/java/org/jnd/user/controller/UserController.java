package org.jnd.user.controller;

import org.jnd.microservices.model.User;
import org.jnd.user.proxies.BasketRepositoryProxy;
import org.jnd.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasketRepositoryProxy basketRepositoryProxy;

    private static int nextId = 0;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    ResponseEntity<?> create(@RequestBody User user, @RequestHeader HttpHeaders headers) {

        //check to see whether user exists
        if (userRepository.containsKey(user.getUsername())) {
            //this user exists : retreive
            user = userRepository.get(user.getUsername());
            log.debug("User exists : " +user);
        }
        else    {
            //this user does not exist : create
            nextId = nextId + 1;
            user.setId(nextId);
            log.debug("User Create : " +user);
            userRepository.put(Integer.toString(user.getId()), user);
        }

        //get user's basket data
        user = basketRepositoryProxy.getBasket(user);

        return new ResponseEntity<>(user, null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    ResponseEntity<?> get(@PathVariable int userId, @RequestHeader HttpHeaders headers) {

        log.debug("Get User : " + userId);
        User user = userRepository.get(Integer.toString(userId));
        log.debug("Get User : " + user);
        return new ResponseEntity<>(user, null, HttpStatus.CREATED);
    }
}
