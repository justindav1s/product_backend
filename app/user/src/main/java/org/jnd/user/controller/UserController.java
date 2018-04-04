package org.jnd.user.controller;

import org.jnd.microservices.model.User;
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

    private static int nextId = 0;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResponseEntity<?> create(User user, @RequestHeader HttpHeaders headers) {

        log.debug("User Create : " +user);

        user.setId(nextId++);
        log.debug("User Create : " +user);
        userRepository.put(Integer.toString(user.getId()), user);
        log.debug("Basket Repository :"+ userRepository);
        return new ResponseEntity<>(user, null, HttpStatus.CREATED);
    }

}
