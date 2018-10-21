package org.jnd.product.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private Log log = LogFactory.getLog(ProductController.class);

    @Value("${my.property:not_found}")
    private String property1 = null;

    @PostConstruct
    public void init()  {
        log.debug("init");
    }

    @RequestMapping(value = "/p1", method = RequestMethod.GET, produces = "application/txt")
    public ResponseEntity<String> config() {

        return new ResponseEntity<>("Property 1 : "+property1, null, HttpStatus.OK);
    }
}
