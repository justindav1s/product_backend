package org.jnd.user.proxies;

import org.jnd.microservices.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("InventoryProxy")
public class InventoryProxy {

    private Log log = LogFactory.getLog(InventoryProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public Product getProduct(String id) {

        log.debug("ProductRepositoryProxy get Product id : "+id);


        ResponseEntity<Product> exchange =
                this.restTemplate.exchange(
                        "http://192.168.0.131:32619/products/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Product>() {},
                        id);

        Product resp = exchange.getBody();
        log.debug("Product Response : "+resp);

        if (resp == null)
            throw new RuntimeException();

        return resp;
    }

    public Object getAllProducts() {

        Object resp = restTemplate.getForObject("http://inventory:8080/products/all", Object.class);

        log.debug("Product Response : "+resp);

        if (resp == null)
            throw new RuntimeException();

        return resp;
    }

}
