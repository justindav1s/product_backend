package org.jnd.user.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component("InventoryProxy")
public class InventoryProxy {

    private Log log = LogFactory.getLog(InventoryProxy.class);

    @Value( "${inventory.host}" )
    String inventory_host;

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

    public List<Product> getAllProducts() {

        List<Product> products = restTemplate.getForObject("http://"+inventory_host+"/products/all", List.class);

        log.debug("Product Response : "+products);

        if (products == null)
            throw new RuntimeException();

        return products;
    }

    public List<Product> getProductsofType(String type) {
        List<Product> products = restTemplate.getForObject("http://"+inventory_host+"/products/type/"+type, List.class);

        log.debug("Product Response : "+products);

        if (products == null)
            throw new RuntimeException();

        return products;
    }

    public List<String> getProductTypes() {
        List<String> products = restTemplate.getForObject("http://"+inventory_host+"/products/types", List.class);

        log.debug("Product types response : "+products);

        if (products == null)
            throw new RuntimeException();

        return products;
    }
}
