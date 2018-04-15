package org.jnd.user.proxies;

import org.jnd.microservices.model.Product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("ProductRepositoryProxy")
public class ProductRepositoryProxy {

    @Value( "${inventory.host}" )
    String inventory_host;

    private Log log = LogFactory.getLog(ProductRepositoryProxy.class);

    private RestTemplate restTemplate = new RestTemplate();;

    public Product getProduct(String id) {

        log.debug("ProductRepositoryProxy get Product id : "+id);


        ResponseEntity<Product> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/{id}",
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
