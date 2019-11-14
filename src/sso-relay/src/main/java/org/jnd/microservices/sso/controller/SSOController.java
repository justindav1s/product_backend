package org.jnd.microservices.sso.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class SSOController {

    private static final Logger log = LoggerFactory.getLogger(SSOController.class);

    @Value( "${sso.server}" )
    String sso_server;

    @RequestMapping(value = "/handle-oauth", method = RequestMethod.GET, produces = "application/json")
    public String login(@RequestHeader HttpHeaders headers, HttpServletRequest request, @RequestParam(name = "code") String code) {

        log.info("handle-oauth");
        String uri = request.getRequestURI();
        Enumeration attrs = request.getAttributeNames();

        log.info("query string  : "+request.getQueryString());


        log.info("uri : "+uri);
        log.info("code : "+code);
        log.info("sso_server : "+sso_server);


        for (String key : headers.keySet()) {
            log.info(key+" : "+headers.get(key));
        }

        return "handle-oauth";
    }



    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }


}
