package org.jnd.microservices.grafeas.proxies;

import org.jnd.microservices.grafeas.controllers.BuildController;
import org.jnd.microservices.grafeas.model.Build;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("GrafeasProxy")
public class GrafeasProxy {

    private static final Logger log = LoggerFactory.getLogger(GrafeasProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    @Value("${grafeas.host:not_found}")
    private String grafeas = null;


    public ResponseEntity<Build> newBuild(Build build) {

        log.debug("new build");

        ResponseEntity<Build> exchange =
                this.restTemplate.exchange(
                        "http://"+grafeas+"/basket/{basketid}/add/{productid}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<Build>() {});

        return exchange;
    }
}
