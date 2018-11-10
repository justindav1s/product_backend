package org.jnd.microservices.product.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class ConfigController {

    private Log log = LogFactory.getLog(ProductController.class);

    // inject via application.properties
    @Value("${config.map.env_name:not_found}")
    private String message = "not_found";

    @PostConstruct
    public void init()  {
        log.debug("init");
    }

    @RequestMapping("/config")
    public String config(Map<String, Object> model) {
        log.debug("request");
        model.put("config_data", this.message);
        return "config";
    }
}
